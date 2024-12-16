package com.minhnghia2k3.book.store.services.impl;

import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.repositories.BookRepository;
import com.minhnghia2k3.book.store.services.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @CachePut(value = "books", key = "#book.isbn")
    @Override
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }


    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Cacheable(value = "books", key = "#isbn")
    @Override
    public Optional<BookEntity> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }


    @CacheEvict(value = "books", key = "#isbn")
    @Override
    public void deleteById(String isbn) {
        bookRepository.deleteById(isbn);
    }

}
