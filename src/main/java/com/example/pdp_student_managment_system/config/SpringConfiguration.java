package com.example.pdp_student_managment_system.config;

import com.example.pdp_student_managment_system.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringConfiguration {
    private final JwtFilter jwtFilter;
    private final String [] WHILE_URL =
            {"/api/v1/auth/register","/api/v1/auth/login",
                    "/post/link/{link}","/post/get-all"
            };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authRequest ->
                                authRequest.requestMatchers(WHILE_URL).permitAll()
                                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
