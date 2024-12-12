package com.minhnghia2k3.book.store.controllers;

import com.minhnghia2k3.book.store.domain.dtos.AuthorDto;
import com.minhnghia2k3.book.store.domain.entities.AuthorEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import com.minhnghia2k3.book.store.services.AuthorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> mapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity author = mapper.fromMapper(authorDto);

        authorService.save(author);

        AuthorDto result = mapper.toMapper(author);

        URI uri = URI.create("api/v1/authors/" + result.getId());

        return ResponseEntity.created(uri).body(result);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> findAuthors() {
        List<AuthorEntity> authors = authorService.findAll();

        List<AuthorDto> results = authors
                .stream()
                .map(mapper::toMapper)
                .toList();

        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> author = authorService.findById(id);

        if(author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AuthorDto result = mapper.toMapper(author.get());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        AuthorEntity author = mapper.fromMapper(authorDto);

        if (!authorService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }

        author.setId(id);
        AuthorEntity updatedAuthor = authorService.save(author);

        return ResponseEntity.ok(mapper.toMapper(updatedAuthor));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if(!authorService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }

        authorService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
