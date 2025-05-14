package com.dima.booksbackend.controllers;


import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.models.Books;
import com.dima.booksbackend.services.BookService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PostMapping("/deleteBook")
    public ResponseEntity<Void> deleteBook(@RequestBody Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateBook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

}
