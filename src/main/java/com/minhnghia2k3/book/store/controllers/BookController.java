package com.minhnghia2k3.book.store.controllers;

import com.minhnghia2k3.book.store.domain.dtos.BookDto;
import com.minhnghia2k3.book.store.domain.entities.BookEntity;
import com.minhnghia2k3.book.store.mappers.Mapper;
import com.minhnghia2k3.book.store.services.BookService;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        BookEntity savedBook = bookService.save(book);
        BookDto result = mapper.toMapper(savedBook);
        URI uri = URI.create("/api/v1/books/" + result.getIsbn());
        return ResponseEntity.created(uri).body(result);
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookDto>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "isbn") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> books = bookService.findAll(pageable);

        Page<BookDto> result = books.map(mapper::toMapper);

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

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> update(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity book = mapper.fromMapper(bookDto);

        if (!bookService.isExists(isbn)) {
            return ResponseEntity.notFound().build();
        }

        BookEntity updatedBook = bookService.save(book);

        return ResponseEntity.ok(mapper.toMapper(updatedBook));
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        if (!bookService.isExists(isbn)) {
            return ResponseEntity.notFound().build();
        }

        bookService.deleteById(isbn);

        return ResponseEntity.noContent().build();
    }
}

