package com.dima.booksbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication_year", nullable = false)
    private int year;

    @Column(name = "rating")
    private double rating;

    @Column(name = "read_year")
    private int readYear;

    @Column(name = "comments", length = 1000)
    private String comments;

    @Column(name = "image_url", length = 500)
    private String image;

    @Column(name = "genre", length = 100)
    private String genre;
}
