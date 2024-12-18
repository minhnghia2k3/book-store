package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserEntityRepositoryIntegrationTests {
    private final UserRepository underTest;

    @Autowired
    public UserEntityRepositoryIntegrationTests(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    void should_create_new_user_successfully() {
        UserEntity user = TestDataUtil.createTestUser();

        UserEntity savedUser = underTest.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(1L);
    }

    @Test
    void should_return_true_when_finding_existing_user_by_email() {
        UserEntity user = TestDataUtil.createTestUser();
        underTest.save(user);
        boolean isExists = underTest.existsByEmail(user.getEmail());

        assertThat(isExists).isTrue();
    }

    @Test
    void should_return_false_when_finding_non_existing_user_by_email(){
        boolean isExists = underTest.existsByEmail("non-existing-user@gmail.com");

        assertThat(isExists).isFalse();
    }

    @Test
    void should_return_true_when_finding_existing_user_by_username() {
        UserEntity user = TestDataUtil.createTestUser();
        underTest.save(user);
        boolean isExists = underTest.existsByUsername(user.getUsername());

        assertThat(isExists).isTrue();
    }

    @Test
    void should_return_false_when_finding_non_existing_user_by_username(){
        boolean isExists = underTest.existsByUsername("non-existing-user");

        assertThat(isExists).isFalse();
    }
}

