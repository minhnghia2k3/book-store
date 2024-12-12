package com.minhnghia2k3.book.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.dtos.AuthorDto;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.print.Book;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    void should_create_new_book_and_return_201_created() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").exists()
        );
    }

    @Test
    void should_find_all_books_and_return_200_ok() throws Exception {
        AuthorEntity author = new AuthorEntity(null, "Somebody i used to know", 15);
        BookEntity bookA =  new BookEntity("1-test-book", "Test Book", author);
        BookEntity bookB =  new BookEntity("2-test-book", "Test Book", author);
        BookEntity bookC =  new BookEntity("3-test-book", "Test Book", author);
        bookService.save(bookA);
        bookService.save(bookB);
        bookService.save(bookC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/books?size=2&page=0&sortBy=isbn&ascending=false")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.*", hasSize(2))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[0].isbn").value(bookC.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[1].isbn").value(bookB.getIsbn())
        );
    }

    @Test
    void should_find_book_and_return_200_ok() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        bookService.save(book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/books/1-test-book")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        );
    }

    @Test
    void should_not_find_book_and_return_404_not_found() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/books/not-exists-book")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void should_update_an_existing_author_and_return_200_ok() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        bookService.save(book);

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value(book.getAuthor().getName())
        );
    }

    @Test
    public void should_not_update_an_existing_book_return_404_not_found() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);

        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void should_delete_a_book_and_return_204_no_content() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        bookService.save(book);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void should_not_delete_a_book_return_404_not_found() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        BookEntity book = TestDataUtil.createTestBook(author);
        bookService.save(book);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/books/not-exist")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}
