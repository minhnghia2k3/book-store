package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.entities.UserEntity;

import java.util.List;

public interface UserService {
    boolean existByUsername(String username);
    boolean existByEmail(String email);
    UserEntity save(UserEntity userEntity);
    List<UserEntity> allUsers();
}
