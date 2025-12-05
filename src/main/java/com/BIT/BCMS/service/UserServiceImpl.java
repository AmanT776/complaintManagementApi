package com.BIT.BCMS.service;

import com.BIT.BCMS.entities.Role;
import com.BIT.BCMS.entities.Users;
import com.BIT.BCMS.repository.RoleRepository;
import com.BIT.BCMS.repository.UserRepository;
import com.BIT.BCMS.config.JWTService; // Assuming you have this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    @Transactional
    public Users create(Users user) {
        // 1. Encode Password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Assign Default Role (ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_USER is not found."));

        user.setRole(userRole);
        return userRepository.save(user);
    }

    // For verifying login (JWT Generation)
    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    @Override
    public Users update(Long id, Users user) {
        Users existing = findById(id);
        existing.setFullName(user.getFullName());
        // Add other fields as needed
        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}