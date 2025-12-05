package com.BIT.BCMS.controller;

import com.BIT.BCMS.entities.Users;
import com.BIT.BCMS.service.UserService;
import com.BIT.BCMS.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final UserServiceImpl service;

    @Autowired
    public UsersController(UserService userService, UserServiceImpl service) {
        this.userService = userService;
        this.service = service;
    }
// Debugging endpoint to view current user's permissions just for test though:)
//    @GetMapping("/debug/me")
//    public ResponseEntity<?> getMyPermissions(org.springframework.security.core.Authentication auth) {
//        if (auth == null) return ResponseEntity.ok("Not Logged In");
//
//        // This will print Roles and Permissions to the screen
//        return ResponseEntity.ok(auth.getAuthorities());
//    }

    @GetMapping
    @PreAuthorize("hasAuthority('USERS_VIEW_ALL')")//connecting with permissions
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PROFILE_VIEW')")//connecting with permissions
    public ResponseEntity<Users> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    // REMOVED @PreAuthorize because guests (unauthenticated) call this
    @PostMapping("/register")
    public ResponseEntity<Users> create(@Valid @RequestBody Users user) {
        Users created = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
    @PostMapping("/login")
    public  String login(@RequestBody Users user) {
       return service.verify(user);//newly added from vid jwt
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")//connecting with permissions
    public ResponseEntity<Users> update(@PathVariable Long id, @Valid @RequestBody Users user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")//connecting with permissions
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

