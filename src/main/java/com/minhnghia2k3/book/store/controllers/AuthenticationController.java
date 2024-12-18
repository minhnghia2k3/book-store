package com.minhnghia2k3.book.store.controllers;

import com.minhnghia2k3.book.store.domain.dtos.LoginUserDto;
import com.minhnghia2k3.book.store.domain.dtos.RegisterUserDto;
import com.minhnghia2k3.book.store.domain.dtos.response.LoginResponse;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.services.AuthenticationService;
import com.minhnghia2k3.book.store.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authService;
    private final JwtService jwtService;
    public AuthenticationController(AuthenticationService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto dto) {
        UserEntity user = authService.signup(dto);

        URI uri = URI.create("/api/v1/users/" + user.getId());

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto dto) {
        UserEntity authenticatedUser = authService.authenticate(dto);

        String jwt = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwt, jwtService.getExpirationTime(jwt));

        return ResponseEntity.ok(loginResponse);
    }
}