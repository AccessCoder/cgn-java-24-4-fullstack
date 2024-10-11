package com.example.backend.security;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository repo;

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

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(){
        DefaultOAuth2UserService defaultUserService = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User user = defaultUserService.loadUser(userRequest);

            AppUser gitHubUser = repo.findById(user.getName()).orElseGet(()-> {
                AppUser newUser = new AppUser(
                        user.getName(),
                        user.getAttributes().get("login").toString(),
                        user.getAttributes().get("avatar_url").toString(),
                        Collections.emptyList()
                );
                return repo.save(newUser);
                    });

            return new DefaultOAuth2User(Collections.emptyList(), user.getAttributes(), "login");
        };
    }

}
