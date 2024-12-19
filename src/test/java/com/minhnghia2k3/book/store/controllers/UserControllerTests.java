package com.minhnghia2k3.book.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.dtos.LoginUserDto;
import com.minhnghia2k3.book.store.domain.dtos.RegisterUserDto;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTests {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final AuthenticationServiceImpl authService;

    @Autowired
    public UserControllerTests(MockMvc mockMvc, ObjectMapper mapper, AuthenticationServiceImpl authService) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.authService = authService;
    }

    @Test
    void should_get_authorized_user() throws Exception {
        // 1. Signup User
        RegisterUserDto user = TestDataUtil.createTestRegisterDto();
        authService.signup(user);

        // 2. Login User to get JWT Token
        LoginUserDto loginDto = TestDataUtil.createTestLoginDto(user);
        String loginJson = mapper.writeValueAsString(loginDto);

        MvcResult loginResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").isNotEmpty()
        ).andReturn();

        // Extract the token from login response
        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.token");

        // 3. Use the token to request "/api/v1/users/me"
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/me")
                        .header("Authorization", "Bearer " + token) // Add token to Authorization header
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail())
        );
    }

    @Test
    void should_not_get_unauthorized_user_providing_malware_jwt() throws Exception {
        // Assume token is malware
        String token = "bad-jwt-token";

        // 1. Use the token to request "/api/v1/users/me"
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/me")
                        .header("Authorization",  "Bearer " + token) // Add token to Authorization header
        ).andExpect(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    void should_not_get_unauthorized_user_providing_non_existing_user_in_jwt() throws Exception {
        // Assume token is malware
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW5obmdoaWExMjMwNUBnbWFpbC5jb20iLCJpYXQiOjE3MzQ1ODYyMjYsImV4cCI6MTczNDU4OTgyNn0.qenOcDr47L8oWXokl0hlCWEi3dzO04unoI3wvHvQA6c";

        // 1. Use the token to request "/api/v1/users/me"
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/me")
                        .header("Authorization",  "Bearer " + token) // Add token to Authorization header
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}
