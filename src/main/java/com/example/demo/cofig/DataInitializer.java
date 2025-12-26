package com.example.demo.cofig;

import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.dto.user.CreateUserDto;
import com.example.demo.model.OrganizationalUnit;
import com.example.demo.model.OrganizationalUnitType;
import com.example.demo.model.Category;
import com.example.demo.model.Role;
import com.example.demo.repository.*;
import com.example.demo.service.role.RoleService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final OrganizationalUnitRepository unitRepository;
    private final OrganizationalUnitTypeRepository typeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> Starting Data Initialization...");

        // 1. Initialize  Roles
        initializeRoles();

        // 2. Initialize Categories
        initializeCategories();

        // 3. Initialize Organization Structure
        initializeOrganization();

        // 4. Initialize Admin User (Requested Style)
        initializeDefaultAdmin();

        log.info(">>> Data Initialization Completed Successfully.");
    }

    @Transactional
    public void initializeRoles() {
        try {
            // --- 3. Save Roles ---
            createRoleIfNotFound("ADMIN");
            createRoleIfNotFound("STAFF");
            createRoleIfNotFound("USER");

        } catch (Exception e) {
            log.error("Error initializing roles: {}", e.getMessage());
        }
    }

    private void initializeCategories() {
        String[] cats = {"Academic Affairs", "Facility & Infrastructure", "Administrative Service", "Financial", "Disciplinary & Conduct"};
        for (String name : cats) {
            if (!categoryRepository.existsByName(name)) {
                Category c = new Category();
                c.setName(name);
                categoryRepository.save(c);
            }
        }
    }

    private void initializeOrganization() {
        // Types
        OrganizationalUnitType uniType = createTypeIfNotFound("UNIVERSITY");
        OrganizationalUnitType facultyType = createTypeIfNotFound("FACULTY");
        OrganizationalUnitType deptType = createTypeIfNotFound("DEPARTMENT");

        // Root: BiT
        OrganizationalUnit bit = createUnitIfNotFound("Bahir Dar Institute of Technology (BiT)", "BiT", uniType, null);

        // Faculty of Computing
        OrganizationalUnit computing = createUnitIfNotFound("Faculty of Computing", "FoC", facultyType, bit);
        createUnitIfNotFound("Software Engineering", "SE", deptType, computing);
        createUnitIfNotFound("Computer Science", "CS", deptType, computing);
        createUnitIfNotFound("Information Technology", "IT", deptType, computing);
        createUnitIfNotFound("Information Systems", "IS", deptType, computing);
        createUnitIfNotFound("Cyber Security", "CSc", deptType, computing);

        // Faculty of Electrical
        OrganizationalUnit electrical = createUnitIfNotFound("Faculty of Electrical & Computer Engineering", "FECE", facultyType, bit);
        createUnitIfNotFound("Computer Engineering", "CE", deptType, electrical);
        createUnitIfNotFound("Electrical Power Engineering", "Power", deptType, electrical);

        // --- FACULTY 3: Mechanical & Industrial Engineering ---
        OrganizationalUnit mechanical = createUnitIfNotFound("Faculty of Mechanical & Industrial Engineering", "FMIE", facultyType, bit);
        createUnitIfNotFound("Mechanical Engineering", "Mech", deptType, mechanical);
        createUnitIfNotFound("Industrial Engineering", "Ind", deptType, mechanical);
        createUnitIfNotFound("Automotive Engineering", "Auto", deptType, mechanical);

        // --- FACULTY 4: Civil & Water Resource Engineering ---
        OrganizationalUnit civil = createUnitIfNotFound("Faculty of Civil & Water Resource Engineering", "FCWRE", facultyType, bit);
        createUnitIfNotFound("Civil Engineering", "Civil", deptType, civil);
        createUnitIfNotFound("Hydraulic & Water Resources", "Hydraulic", deptType, civil);

        // --- FACULTY 5: Chemical & Food Engineering ---
        OrganizationalUnit chemical = createUnitIfNotFound("Faculty of Chemical & Food Engineering", "FCFE", facultyType, bit);
        createUnitIfNotFound("Chemical Engineering", "Chem", deptType, chemical);
        createUnitIfNotFound("Food Engineering", "Food", deptType, chemical);

    }

    private void initializeDefaultAdmin() {
        try {
            // Create default admin user if not exists
            String adminEmail = "admin@university.edu";
            if (!userService.existsByEmail(adminEmail)) {
                RoleResponseDTO adminRole = Optional.ofNullable(roleService.findByName("ADMIN"))
                        .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

                CreateUserDto adminDto = new CreateUserDto();
                adminDto.setFirstName("System");
                adminDto.setLastName("Administrator");
                adminDto.setEmail(adminEmail);
                adminDto.setPassword("admin123"); // Change this in production
                adminDto.setRoleId(adminRole.getId());

                userService.createUser(adminDto);
                log.info("Created default admin user with email: {}", adminEmail);
                log.info("Default admin password: admin123 (Please change this in production!)");
            }

        } catch (Exception e) {
            log.error("Error initializing default admin: {}", e.getMessage());
        }
    }

    // --- Helper Methods ---

    private void createRoleIfNotFound(String name) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role();
            role.setName(name);
            role.setIsActive(true);
            roleRepository.save(role);
            log.info("Created role: {}", name);
        }
    }

    private OrganizationalUnitType createTypeIfNotFound(String name) {
        if (!typeRepository.existsByName(name)) {
            OrganizationalUnitType type = new OrganizationalUnitType();
            type.setName(name);
            return typeRepository.save(type);
        }
        return typeRepository.findByName(name).orElse(null);
    }

    private OrganizationalUnit createUnitIfNotFound(String name, String abbr, OrganizationalUnitType type, OrganizationalUnit parent) {
        if (!unitRepository.existsByName(name)) {
            OrganizationalUnit unit = new OrganizationalUnit();
            unit.setName(name);
            unit.setAbbreviation(abbr);
            unit.setUnitType(type);
            unit.setParent(parent);
            return unitRepository.save(unit);
        }
        List<OrganizationalUnit> units = unitRepository.findByName(name);
        return units.isEmpty() ? null : units.get(0);
    }
}