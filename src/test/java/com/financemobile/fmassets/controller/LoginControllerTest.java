package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.LoginDto;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends OAuth2Helper {

    @Test
    public void shouldLoginSuccessfully() throws Exception {

        Gson gson = new Gson();
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("me@gmail.com");
        loginDto.setPassword("password");

        mockMvc.perform(post("/auth/login")
                .content(gson.toJson(loginDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.access_token", not("")))
                .andExpect(jsonPath("data.user.email", is(loginDto.getUsername())));
    }

    @Test
    public void loginUnSuccessfully() throws Exception {

        Gson gson = new Gson();
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("me@gmail.com");
        loginDto.setPassword("password22");

        mockMvc.perform(post("/auth/login")
                .content(gson.toJson(loginDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("failed")))
                .andExpect(jsonPath("data", is("bad credentials")));
    }

    @Test
    public void shouldLogoutSuccessfully() throws Exception {

        mockMvc.perform(get("/auth/logout?token=" + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data", is(true)));
    }

    @Test
    public void invalidSession() throws Exception {

        mockMvc.perform(get("/auth/logout?token=7239203909030299442dereer")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("failed")))
                .andExpect(jsonPath("data", is("session not found.")));
    }

}
