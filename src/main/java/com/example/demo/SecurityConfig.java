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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;


    // Constructor injection (recommended over @Autowired fields)
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CorsConfigurationSource corsConfigurationSource) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz

                        // ✅ PUBLIC ENDPOINTS FIRST
                        .requestMatchers(
                                "/uploads/**",
                                "/auth/**",
                                "/public/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/users/exists/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/compliant/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/compliant/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/org/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/units/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/org/categories/active").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/compliant/reference/{referenceNumber}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/unit-types").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/me").permitAll()

                        // 🔐 ROLE-BASED
                        .requestMatchers(HttpMethod.GET, "/api/v1/compliant/{id}/status").hasRole("STAFF")
                        .requestMatchers("/api/v1/admin/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/profile/user/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/v1/profile/organizational-unit/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/api/v1/profile/staff").hasAnyRole("ADMIN", "STAFF")

                        // 🔒 AUTHENTICATED
                        .requestMatchers(
                                "/api/v1/profile/me",
                                "/api/v1/profile/change-password"
                        ).authenticated()

                        // ✅ MUST BE LAST
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}