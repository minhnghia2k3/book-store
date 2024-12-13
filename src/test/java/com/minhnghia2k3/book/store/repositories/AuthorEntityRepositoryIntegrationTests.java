package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static  org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository authorRepository) {
        this.underTest = authorRepository;
    }

    @Test
    public void should_create_new_author_and_recall() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(authorEntity.getId());
    }

    @Test
    public void should_find_all_authors() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);

        Iterable<AuthorEntity> authors = underTest.findAll();

        assertThat(authors).isNotNull();
        assertThat(authors).hasSize(1);
    }

    @Test
    public void should_update_existing_author() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);

        authorEntity.setName("updated-name");
        authorEntity.setAge(30);
        underTest.save(authorEntity);

        Optional<AuthorEntity> result = underTest.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(authorEntity.getName());
        assertThat(result.get().getAge()).isEqualTo(authorEntity.getAge());
    }

    @Test
    public void should_delete_existing_author() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);

        underTest.deleteById(authorEntity.getId());

        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void givenMinAge30_WhenFindAgeLessThan30_thenSuccess() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);

        AuthorEntity authorEntityB = new AuthorEntity(null, "Tom Holland", 25);
        underTest.save(authorEntityB);

        AuthorEntity authorEntityC = new AuthorEntity(null, "James Bone", 30);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(30);
        assertThat(result).hasSize(1);
    }
}
