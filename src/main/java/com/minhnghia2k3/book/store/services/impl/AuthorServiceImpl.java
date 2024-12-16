package com.minhnghia2k3.book.store.services.impl;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.exceptions.AuthorDeletionException;
import com.minhnghia2k3.book.store.repositories.AuthorRepository;
import com.minhnghia2k3.book.store.repositories.BookRepository;
import com.minhnghia2k3.book.store.services.AuthorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @CachePut(value = "authors", key = "#author.id")
    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }


    @Cacheable(value = "authors", key = "#id")
    @Override
    public Optional<AuthorEntity> findById(Long id) {
        System.out.println("Fetching product from database...");
        return authorRepository.findById(id);
    }

    @Cacheable(value = "authors")
    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository.findAllOrderByIdDesc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @CacheEvict(value = "authors", key = "#id")
    @Override
    public void deleteById(Long id) {
        // Check books of authors
        if(!bookRepository.findByAuthorEntity_Id(id).isEmpty()) {
            throw new AuthorDeletionException("cannot delete author with existing books.");
        }
        authorRepository.deleteById(id);
    }
}
