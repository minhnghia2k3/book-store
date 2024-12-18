package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.dtos.LoginUserDto;
import com.minhnghia2k3.book.store.domain.dtos.RegisterUserDto;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;

public interface AuthenticationService {
    UserEntity signup(RegisterUserDto input);
    UserEntity authenticate(LoginUserDto input);
}
