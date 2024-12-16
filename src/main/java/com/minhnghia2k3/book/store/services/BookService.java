package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    BookEntity save(BookEntity book);

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findById(String isbn);

    boolean isExists(String isbn);

    void deleteById(String isbn);
}
