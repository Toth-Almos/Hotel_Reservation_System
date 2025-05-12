package com.toth_almos.hotelreservationsystem.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/hotels", "api/v1/hotels/{id}", "api/v1/hotels/filtered" ).permitAll()
                        .requestMatchers("/api/v1/rooms/{id}", "/api/v1/rooms/get-by-hotel/{id}").permitAll()
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/logout", "/api/v1/auth/current-user", "/api/v1/auth/register", "/api/v1/auth/change-password").permitAll()
                        .requestMatchers("/api/v1/user/{customerId}", "/api/v1/user/update-profile/{customerId}").hasRole("CUSTOMER")
                        .requestMatchers("/api/v1/reservation/create-reservation").hasRole("CUSTOMER")
                        .requestMatchers("/api/reservation/delete-reservation/{id}", "/api/reservation/get-reservations/{id}", "/api/reservation/get-active-reservations/{id}").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/api/v1/review/get-hotel-reviews/{hotelId}", "/api/v1/review/get-customer-reviews/{customerId}").permitAll()
                        .requestMatchers("/api/v1/review/delete-review/{id}", "/api/v1/review/create-review").hasRole("CUSTOMER")
                        .requestMatchers("/api/v1/hotels/create-hotel", "api/v1/hotels/update-hotel/{id}").hasRole("ADMIN")
                        .requestMatchers("/api/v1/rooms/create-room", "/api/v1/rooms/update/{id}", "/api/v1/rooms/delete/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated() // All other routes require authentication
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );

        return http.build();
    }
}
