package com.dima.booksbackend.services;

import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Integer book) {
        bookRepository.deleteById(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
}
