package com.minhnghia2k3.book.store.services.impl;

import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.repositories.UserRepository;
import com.minhnghia2k3.book.store.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existByUsername(String username) {
        return false;
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }
}
