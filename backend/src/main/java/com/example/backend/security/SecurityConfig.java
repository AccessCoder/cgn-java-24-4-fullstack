package com.example.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${APP_URL}")
    private String appUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(r -> r
                        .requestMatchers("/api/book/*").permitAll()
                        .requestMatchers("/api/book").authenticated()
                        .anyRequest().permitAll()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(logout -> logout.logoutSuccessUrl(appUrl).logoutUrl("/api/auth/logout"))
                .oauth2Login(login -> login.defaultSuccessUrl(appUrl+"/dashboard"));
        return httpSecurity.build();

    }

}
