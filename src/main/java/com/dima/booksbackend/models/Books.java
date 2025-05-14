package com.dima.booksbackend.models;

import java.util.List;

public class Books {
    private List<Book> books;

    public Books(List<Book> books) {
        this.books = books;
    }
    public List<Book> getBooks() {
        return books;
    }
}
