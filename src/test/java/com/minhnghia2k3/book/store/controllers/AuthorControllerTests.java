package com.minhnghia2k3.book.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhnghia2k3.book.store.TestDataUtil;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerTests(MockMvc mockMvc, ObjectMapper mapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.mapper = new ObjectMapper();
    }

    @Test
    public void should_create_an_user_and_return_201_created() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        String authorJson = mapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void should_return_all_authors_and_200_ok() throws Exception {
        AuthorEntity authorA = new AuthorEntity(null, "Tobey Maguire", 40);
        AuthorEntity authorB = new AuthorEntity(null, "John Doe", 20);
        AuthorEntity authorC = new AuthorEntity(null, "Somebody i used to know", 15);

        authorService.createAuthor(authorA);
        authorService.createAuthor(authorB);
        authorService.createAuthor(authorC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/authors")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$", hasSize(3))
        );
    }

    @Test
    public void should_return_an_author_and_200_ok() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor();
        authorService.createAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/authors/1")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        );
    }

    @Test
    public void should_not_return_author_204_not_found() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/authors/99")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}