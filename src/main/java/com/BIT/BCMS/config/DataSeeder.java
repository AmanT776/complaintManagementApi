package com.BIT.BCMS.config;

import com.BIT.BCMS.entities.Role;
import com.BIT.BCMS.entities.Users;
import com.BIT.BCMS.repository.RoleRepository;
import com.BIT.BCMS.repository.UserRepository;
import com.BIT.BCMS.entities.Permission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // 1. Roles
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN",
                EnumSet.allOf(Permission.class));

        Role officerRole = createRoleIfNotFound("ROLE_COMP_OFFICER",
                EnumSet.of(
                        Permission.COMPLAINT_VIEW_ALL,
                        Permission.COMPLAINT_DELETE_ANY,
                        Permission.COMPLAINT_UPDATE_STATUS,
                        Permission.COMPLAINT_ADD_INTERNAL_NOTE

                        ));

        Role userRole = createRoleIfNotFound("ROLE_USER",
                EnumSet.of(
                        Permission.COMPLAINT_CREATE,
                        Permission.COMPLAINT_VIEW_OWN,
                        Permission.COMPLAINT_UPDATE_CONTENT,
                        Permission.COMPLAINT_DELETE,
                        Permission.COMPLAINT_ADD_COMMENT,
                        Permission.COMPLAINT_VIEW_HISTORY,
                        Permission.COMPLAINT_ATTACH_FILE,
                        Permission.COMPLAINT_VIEW_HISTORY,

                        Permission.USER_CREATE,
                        Permission.USER_PROFILE_VIEW,
                        Permission.USER_UPDATE,
                        Permission.USER_DELETE





                        ));

        // 2. Admin User
        if (userRepository.findByUsername("hakim") == null) {
            Users admin = new Users();
            admin.setUsername("hakim");
            admin.setPassword(passwordEncoder.encode("haha"));
            admin.setFullName("Hakim Admin");
            admin.setRole(adminRole); // <--- CHANGED to singular
            userRepository.save(admin);
            System.out.println("Seeded: hakim (ROLE_ADMIN)");
        }

        // 3. Officer User
        if (userRepository.findByUsername("abdu") == null) {
            Users officer = new Users();
            officer.setUsername("abdu");
            officer.setPassword(passwordEncoder.encode("haha"));
            officer.setFullName("Abdu Officer");
            officer.setRole(officerRole); // <--- CHANGED to singular
            userRepository.save(officer);
            System.out.println("Seeded: abdu (ROLE_COMP_OFFICER)");
        }
    }

    private Role createRoleIfNotFound(String name, java.util.Set<Permission> permissions) {
        Optional<Role> roleOpt = roleRepository.findByName(name);

        if (roleOpt.isPresent()) {
            Role role = roleOpt.get();
            // FORCE UPDATE permissions to match the code
            role.setPermissions(permissions);
            return roleRepository.save(role);
        } else {
            return roleRepository.save(Role.builder()
                    .name(name)
                    .permissions(permissions)
                    .build());
        }
    }}