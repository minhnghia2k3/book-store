package com.minhnghia2k3.book.store.domain.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class BookEntity implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade: allow creates new author instance for book
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    public BookEntity() {
    }

    public BookEntity(String isbn, String title, AuthorEntity authorEntity) {
        this.isbn = isbn;
        this.title = title;
        this.authorEntity = authorEntity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorEntity getAuthor() {
        return authorEntity;
    }

    public void setAuthor(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + authorEntity +
                '}';
    }
}
