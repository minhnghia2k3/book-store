package com.minhnghia2k3.book.store;

import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
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
}
