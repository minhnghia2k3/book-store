package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, String>, JpaRepository<BookEntity, String> {
    List<BookEntity> findByAuthorEntity_Id(Long authorId);
}
