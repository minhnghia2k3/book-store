package com.minhnghia2k3.book.store;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;
import com.minhnghia2k3.book.store.domain.entities.UserEntityBuilder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDataUtil {
    public TestDataUtil() {
    }

    public static AuthorEntity createTestAuthor() {
        return new AuthorEntity(null, "Tobey Maguire", 40);
    }

    public static BookEntity createTestBook(AuthorEntity authorEntity) {
        return new BookEntity("1-test-book", "Test Book", authorEntity);
    }

    public static UserEntity createTestUser() {
        return new UserEntityBuilder().setUsername("unique_username").setEmail("unique@username.com").setPassword("pw@123").createUserEntity();
    }
}
