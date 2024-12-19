package com.minhnghia2k3.book.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationControllerTests {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationControllerTests(MockMvc mockMvc, ObjectMapper mapper, AuthenticationServiceImpl authenticationService) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.authenticationService = authenticationService;
    }

    @Test
    void should_register_a_new_user_and_return_201() throws Exception {
        UserEntity user = TestDataUtil.createTestUser();
        String userJson = mapper.writeValueAsString(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.header().stringValues("Location", "/api/v1/users/1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").doesNotExist()
        );
    }

    @Test
    void should_not_register_existing_user_and_return_409() throws Exception {
        RegisterUserDto dto = TestDataUtil.createTestRegisterDto();
        authenticationService.signup(dto);

        String userJson = mapper.writeValueAsString(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
        );
    }

    @Test
    void should_return_unauthorized_when_password_is_invalid() throws Exception {
        RegisterUserDto dto = TestDataUtil.createTestRegisterDto();
        authenticationService.signup(dto);

        LoginUserDto loginDto = new LoginUserDto(dto.getEmail(), "invalid-password");
        String loginJson = mapper.writeValueAsString(loginDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void should_return_unauthorized_when_email_is_invalid() throws Exception {
        RegisterUserDto dto = TestDataUtil.createTestRegisterDto();
        authenticationService.signup(dto);

        LoginUserDto loginDto = new LoginUserDto("invalid-email@gmail.com", dto.getPassword());
        String loginJson = mapper.writeValueAsString(loginDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void should_return_jwt_token_when_login_successfully() throws Exception {
        RegisterUserDto dto = TestDataUtil.createTestRegisterDto();
        authenticationService.signup(dto);

        LoginUserDto loginDto = new LoginUserDto(dto.getEmail(), dto.getPassword());
        String loginJson = mapper.writeValueAsString(loginDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").isNotEmpty()
        );
    }
}
