package com.example.pdp_student_managment_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringConfiguration {
    // bu yerga jwtFilter kelishi kerak
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
                //jwt filterni spring ni filteridan oldin register qilish kerak
                .build();
    }
}
