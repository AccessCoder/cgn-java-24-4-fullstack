package com.example.backend.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/me")
    public String getMe(){ //GitHub ID auslesen!
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/me/2")
    public String getMe2(@AuthenticationPrincipal OAuth2User user){ //Username auslesen!
        if (user == null){
            return "anonymousUser";
        }
        return user.getAttributes()
                .get("login")
                .toString();
    }

}
