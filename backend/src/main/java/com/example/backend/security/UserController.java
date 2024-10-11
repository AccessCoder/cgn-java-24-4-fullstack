package com.example.backend.security;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    @GetMapping("/me")
    public String getMe(){ //GitHub ID auslesen!
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/me/2")
    public AppUser getMe2(@AuthenticationPrincipal OAuth2User user){ //Username auslesen!
        if (user == null){
            return new AppUser("NotFound","anonymousUser", null, null );
        }
        return repo.findById(user.getName()).orElseThrow();
    }

}
