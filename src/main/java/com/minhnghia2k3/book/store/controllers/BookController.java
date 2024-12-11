package com.minhnghia2k3.book.store.controllers;

import com.minhnghia2k3.book.store.domain.dtos.BookDto;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import com.minhnghia2k3.book.store.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final Mapper<BookEntity, BookDto> mapper;
    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> mapper, BookService bookService) {
        this.mapper = mapper;
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookEntity book = mapper.fromMapper(bookDto);
        bookService.createBook(book);
        BookDto result = mapper.toMapper(book);
        URI uri = URI.create("/api/v1/books/" + result.getIsbn());
        return ResponseEntity.created(uri).body(result);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookEntity> books = bookService.findAll();

        List<BookDto> result = books.stream()
                .map(mapper::toMapper)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> findBookById(@PathVariable String isbn) {
        Optional<BookEntity> book = bookService.findById(isbn);

        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapper.toMapper(book.get()));
    }
}

