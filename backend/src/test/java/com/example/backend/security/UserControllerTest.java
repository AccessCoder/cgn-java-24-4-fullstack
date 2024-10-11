package com.example.backend.security;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "Currywurst")
    void getMe() throws Exception {
        mvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("Currywurst"));
    }

    @Test
    void getMe2() throws Exception {
        AppUser testUser = new AppUser("user", "Currywurst", "Currywurst", Collections.emptyList());
        userRepository.save(testUser);

        mvc.perform(get("/api/auth/me/2")
                        .with(oidcLogin().userInfoToken(token ->{
                            token.claim("login", "Currywurst")
                                 .claim("avatar_url", "Currywurst");
                                }

                        )))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                        {
                                            "id": "user",
                                            "username": "Currywurst",
                                            "avatarUrl": "Currywurst",
                                            "favList": []
                                        }
                                        """));
    }
}