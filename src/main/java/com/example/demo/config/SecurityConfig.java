package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Data
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
//        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/exists/**").permitAll()
                        // Admin only endpoints
                        .requestMatchers("/api/users").hasRole("ADMIN") // POST /api/users (createUser)
                        .requestMatchers("/api/users/*/reset-password").hasRole("ADMIN")
                        .requestMatchers("/api/users/*/activate").hasRole("ADMIN")
                        .requestMatchers("/api/users/*/deactivate").hasRole("ADMIN")
                        // Admin and Staff endpoints
                        .requestMatchers("/api/users/admins").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/users/staff").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/users/regular-users").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/users/organizational-unit/**").hasAnyRole("ADMIN", "STAFF")
                        // Authenticated user endpoints
                        .requestMatchers("/api/users/*/change-password").authenticated()
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "STAFF") // GET requests
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}