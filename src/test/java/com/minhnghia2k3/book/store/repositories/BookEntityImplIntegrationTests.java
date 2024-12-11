package com.minhnghia2k3.book.store.repositories;

import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityImplIntegrationTests {
    private BookRepository underTest;

    @Autowired
    public BookEntityImplIntegrationTests(BookRepository bookRepository) {
        this.underTest = bookRepository;
    }

    @Test
    public void should_create_book_and_recall() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById("1-test-book");

        assertThat(result).isPresent();
        assertThat(result.get().getIsbn()).isEqualTo(bookEntity.getIsbn());
    }

    @Test
    public void should_find_all_books() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        underTest.save(bookEntity);

        Iterable<BookEntity> books = underTest.findAll();

        assertThat(books).isNotNull();
        assertThat(books).hasSize(1);
    }

    @Test
    public void should_update_book() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        underTest.save(bookEntity);

        // update fields
        bookEntity.setTitle("new title");
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById("1-test-book");

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(bookEntity.getTitle());
    }

    @Test
    public void should_delete_book() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();
    }

}
