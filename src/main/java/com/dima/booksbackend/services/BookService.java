package com.dima.booksbackend.services;

import com.dima.booksbackend.exceptions.BookNotFoundException;
import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public void deleteBook(Integer book) {
        bookRepository.deleteById(book);
    }

    public Book updateBook(Book book) {

        if (bookRepository.existsById(book.getId())) {
            return bookRepository.save(book);
        }
        throw new BookNotFoundException("Book with id: " + book.getId() + " doesn't exist");
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
