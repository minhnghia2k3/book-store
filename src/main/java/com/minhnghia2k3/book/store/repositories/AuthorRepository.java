package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("FROM AuthorEntity WHERE age >= :age")
    Iterable<AuthorEntity> findAgeGreaterOrEqualThan(int age);

    @Query("FROM AuthorEntity ORDER BY id desc")
    Iterable<AuthorEntity> findAllOrderByIdDesc();

}
