package com.dima.booksbackend.services;

import com.dima.booksbackend.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
}
