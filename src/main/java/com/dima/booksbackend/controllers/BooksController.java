package com.dima.booksbackend.controllers;

import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    @GetMapping("/getBooks")
    public ResponseEntity<Map<String, List<Book>>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(Map.of("books", books));
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Map<String, Book> requestBody) {
        Book book = requestBody.get("book");
        if (book == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook); // Use 201 Created
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<Map<String, String>> deleteBook(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("id");
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book deleted with ID: " + id)); // 200 OK with message
    }


    @PatchMapping("/updateBook")
    public ResponseEntity<Map<String, String>> updateBook(@RequestBody Map<String, Book> requestBody) {
        Book book = requestBody.get("book");
        if (book == null || book.getId() == 0) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        bookService.updateBook(book);
        return ResponseEntity.ok(Map.of("message", "Book with ID " + book.getId() + " updated"));
    }

}