package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, String> {
}
