package com.minhnghia2k3.book.store;

import com.minhnghia2k3.book.store.domain.entities.RoleEntity;
import com.minhnghia2k3.book.store.domain.entities.enums.ERole;
import com.minhnghia2k3.book.store.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BookStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            // Seeding roles to database
            ERole[] roleNames = new ERole[]{ERole.USER, ERole.ADMIN, ERole.SUPER_ADMIN};

            Arrays.stream(roleNames).forEach(role -> {
                Optional<RoleEntity> optionalRole = roleRepository.findByName(role);

                if (optionalRole.isEmpty()) {
                    RoleEntity roleEntity = new RoleEntity(role);
                    roleRepository.save(roleEntity);
                }
            });
        };
    }
}
