package com.minhnghia2k3.book.store.services;

import com.minhnghia2k3.book.store.domain.entities.enums.ERole;

public interface RoleService {
    boolean existByName(ERole name);
}
