package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createBook(BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findById(String isbn);
}
