package com.example.demo;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(httpBasic -> httpBasic.disable())
//                .formLogin(form -> form.disable());
//
//        return http.build();
//    }
    @Autowired
    private   UserDetailsService userDetailsService ;
   @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
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
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints - auth endpoints
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/users/exists/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/compliant/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/compliant/{id}").permitAll()
                        // Admin only endpoints
                        .requestMatchers("/api/v1/admin/users/**").hasRole("ADMIN")
                        // Profile endpoints - authenticated users can access their own profile
                        .requestMatchers("/api/v1/profile/me").authenticated()
                        .requestMatchers("/api/v1/profile/change-password").authenticated()
                        // Profile endpoints - staff and admin can view other users
                        .requestMatchers("/api/v1/profile/user/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/v1/profile/organizational-unit/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/v1/profile/staff").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.GET,"/api/v1/org/categories").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/units/**").permitAll()
                        // Public profile endpoints for validation
                        .requestMatchers("/api/v1/profile/exists/**").permitAll()
                        // Swagger/OpenAPI endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()

                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
