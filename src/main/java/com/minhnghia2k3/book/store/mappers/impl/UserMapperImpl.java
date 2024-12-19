package com.minhnghia2k3.book.store.mappers.impl;

import com.minhnghia2k3.book.store.domain.dtos.response.UserResponse;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserResponse> {
    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse toMapper(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    @Override
    public UserEntity fromMapper(UserResponse userResponse) {
        return modelMapper.map(userResponse, UserEntity.class);
    }
}
