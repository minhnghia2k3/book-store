package com.minhnghia2k3.book.store.mappers.impl;

import com.minhnghia2k3.book.store.domain.dtos.BookDto;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto toMapper(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity fromMapper(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
