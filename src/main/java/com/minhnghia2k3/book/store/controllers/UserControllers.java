package com.minhnghia2k3.book.store.controllers;

import com.minhnghia2k3.book.store.domain.dtos.response.UserResponse;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import com.minhnghia2k3.book.store.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllers {

    private final UserService userService;
    private final Mapper<UserEntity, UserResponse> mapper;

    public UserControllers(UserService userService, Mapper<UserEntity, UserResponse> mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        UserResponse response = mapper.toMapper(currentUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping
//    TODO: @RolesAllowed("ROLE_SUPER_ADMIN")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }
}
