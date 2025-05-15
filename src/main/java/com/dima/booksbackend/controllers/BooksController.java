package com.dima.booksbackend.controllers;

import com.dima.booksbackend.exceptions.BookNotFoundException;
import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<Map<String, Page<Book>>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false, defaultValue = "readYear") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Page<Book> books = bookService.getBooks(
                PageRequest.of(page, size, Sort.by(sortDirection, sortBy))
        );
        return ResponseEntity.ok(Map.of("books", books));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Map<String, Book> requestBody) {
        Book book = requestBody.get("book");
        if (book == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook); // Use 201 Created
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book deleted with ID: " + id)); // 200 OK with message
    }

    @PatchMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> updateBook(@RequestBody Map<String, Book> requestBody) {

        Book book = requestBody.get("book");
        if (book == null || book.getId() == 0) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        try {
            bookService.updateBook(book);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Book with ID " + book.getId() + " updated"));
    }
}