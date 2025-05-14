package com.dima.booksbackend.repositories;

import com.dima.booksbackend.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsById(Integer id);

    Page<Book> findAll(Pageable pageable);

}
