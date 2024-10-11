package com.example.backend.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public record AppUser(String id,
                      String username,
                      String avatarUrl,
                      List<String> favList) {
}
