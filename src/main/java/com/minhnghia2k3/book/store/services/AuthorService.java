package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findById(Long id);
}