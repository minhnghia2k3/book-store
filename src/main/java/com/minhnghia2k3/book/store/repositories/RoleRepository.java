package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.domain.entities.RoleEntity;
import com.minhnghia2k3.book.store.domain.entities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(ERole name);
}
