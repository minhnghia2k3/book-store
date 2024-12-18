package com.minhnghia2k3.book.store.domain.entities;

import com.minhnghia2k3.book.store.domain.entities.enums.ERole;
import jakarta.persistence.*;

import java.util.HashSet;

@Entity
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(length=20)
    private ERole name;

    @ManyToMany(mappedBy="roles")
    private HashSet<UserEntity> users;

    public RoleEntity() {
    }

    public RoleEntity(Integer id, ERole name, HashSet<UserEntity> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public HashSet<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(HashSet<UserEntity> users) {
        this.users = users;
    }
}
