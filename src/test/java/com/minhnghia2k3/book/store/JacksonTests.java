package com.minhnghia2k3.book.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {

    @Test
    public void test_that_object_mapper_can_create_json_from_java_object() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        AuthorEntity authorEntity = new AuthorEntity(1L, "first author", 40);

        String result = om.writeValueAsString(authorEntity);

        assertThat(result).isEqualTo("{\"id\":1,\"name\":\"first author\",\"age\":40}");
    }

    @Test
    public void test_that_object_mapper_can_create_java_object_from_json() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        AuthorEntity authorEntity = new AuthorEntity(1L, "first author", 40);

        String json = "{\"id\":1,\"name\":\"first author\",\"age\":40}";

        AuthorEntity result = objectMapper.readValue(json, AuthorEntity.class);

        assertThat(result).usingRecursiveComparison().isEqualTo(authorEntity);
    }
}
