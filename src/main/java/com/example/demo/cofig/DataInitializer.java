package com.example.demo.cofig; // ✅ FIXED package name

import com.example.demo.dto.organizationalUnit.OrganizationalUnitRequestDTO;
import com.example.demo.dto.role.RoleResponseDTO;
import com.example.demo.dto.user.CreateUserDto;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import com.example.demo.service.organizationalUnitTypeService.OrganizationalUnitTypeService;
import com.example.demo.service.role.RoleService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;


    @Override
    public void run(String... args) {
        log.info("Running DataInitializer...");

        initializeRoles();
        initializeDefaultAdmin();

        log.info("✅ DataInitializer finished");
    }



    private void initializeRoles() {
        try {
            if (!roleService.existsByName("ADMIN")) {
                roleService.create("ADMIN", "System Administrator with full access");
                log.info("Created ADMIN role");
            }

            if (!roleService.existsByName("STAFF")) {
                roleService.create("STAFF", "Staff members who handle complaints");
                log.info("Created STAFF role");
            }

            if (!roleService.existsByName("USER")) {
                roleService.create("USER", "Regular users who can submit complaints");
                log.info("Created USER role");
            }
        } catch (Exception e) {
            log.error("❌ Error initializing roles", e);
        }
    }







    private void initializeDefaultAdmin() {
        try {
            String adminEmail = "admin@university.edu";

            if (userService.existsByEmail(adminEmail)) {
                log.info("Admin user already exists");
                return;
            }

            RoleResponseDTO adminRole = Optional.ofNullable(roleService.findByName("ADMIN"))
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            CreateUserDto adminDto = new CreateUserDto();
            adminDto.setFirstName("System");
            adminDto.setLastName("Administrator");
            adminDto.setEmail(adminEmail);
            adminDto.setPassword("admin123");
            adminDto.setRoleId(adminRole.getId());

            userService.createUser(adminDto);

            log.info("✅ Default admin created: {}", adminEmail);
            log.warn(" Default password is 'admin123' — change it in production!");

        } catch (Exception e) {
            log.error("❌ Error initializing default admin", e);
        }
    }
}
