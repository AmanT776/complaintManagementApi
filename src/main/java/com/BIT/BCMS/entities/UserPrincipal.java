package com.BIT.BCMS.entities;

import com.BIT.BCMS.entities.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserPrincipal implements UserDetails {

    private final Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

         // 1. Get the single Role
        Role role = user.getRole();
        if (role != null) {
            // Add the Role (e.g., "ROLE_ADMIN")
            authorities.add(new SimpleGrantedAuthority(role.getName()));

            // 2. Add granular Permissions (e.g., "COMPLAINT_DELETE_ANY")
            // This allows you to use @PreAuthorize("hasAuthority('COMPLAINT_DELETE_ANY')")
            for (Permission p : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(p.name()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // Helper to get ID if needed in controller
    public Long getId() { return user.getId(); }
}