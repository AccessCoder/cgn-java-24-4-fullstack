package com.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser
    void test1_shouldReturnHello_whenCalledWithValidCredentials() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"));
    }

    @Test
    void test1_shouldReturn302_whenCalledWithInvalidCredentials() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(get("/api/book"))
                .andExpect(status().is(302));
    }
}