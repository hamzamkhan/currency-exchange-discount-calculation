package org.example.cedc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Configuration
public class SecurityConfig {

    public static final String[] WHITELISTED_APIS = {
            "/api/user/create",
    };

    @Bean
    public SecurityFilterChain whitelistFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(WHITELISTED_APIS) // Apply only to whitelisted APIs
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow access without authentication
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Require authentication for all other APIs
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()));
        return http.build();
    }
}
