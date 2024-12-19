package com.minhnghia2k3.book.store.services.impl;

import com.minhnghia2k3.book.store.domain.dtos.LoginUserDto;
import com.minhnghia2k3.book.store.domain.dtos.RegisterUserDto;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.domain.entities.UserEntityBuilder;
import com.minhnghia2k3.book.store.exceptions.UserExistsException;
import com.minhnghia2k3.book.store.repositories.UserRepository;
import com.minhnghia2k3.book.store.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserEntity signup(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new UserExistsException("User already exists!");
        }

        UserEntity user = new UserEntityBuilder()
                .setEmail((input.getEmail()))
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .createUserEntity();

        return userRepository.save(user);
    }

    @Override
    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
