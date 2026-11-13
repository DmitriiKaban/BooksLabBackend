package com.dima.booksbackend.controllers;

import com.dima.booksbackend.exceptions.BookNotFoundException;
import com.dima.booksbackend.models.Book;
import com.dima.booksbackend.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Books", description = "Books management API")
@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    @Operation(
            summary = "Get all books",
            description = "Returns a paginated list of books with sorting options",
            security = {} // This removes the global security requirement for GET
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books found",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content)
    })
    @GetMapping()
    public ResponseEntity<Map<String, Page<Book>>> getBooks(
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "30") int size,
            @Parameter(description = "Field to sort by") @RequestParam(required = false, defaultValue = "readYear") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)") @RequestParam(required = false, defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Page<Book> books = bookService.getBooks(
                PageRequest.of(page, size, Sort.by(sortDirection, sortBy))
        );
        return ResponseEntity.ok(Map.of("books", books));
    }

    @Operation(
            summary = "Add a new book",
            description = "Creates a new book entry",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid book data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content)
    })
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Map<String, Book> requestBody) {
        Book book = requestBody.get("book");
        if (book == null) {
            return ResponseEntity.badRequest().build();
        }
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted"),
            @ApiResponse(responseCode = "401", description = "Authentication required",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteBook(
            @Parameter(description = "ID of the book to delete") @PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book deleted with ID: " + id));
    }

    @Operation(
            summary = "Update a book",
            description = "Updates an existing book",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "400", description = "Invalid book data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Authentication required",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PatchMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> updateBook(@RequestBody Map<String, Book> requestBody) {
        Book book = requestBody.get("book");
        if (book == null || book.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            bookService.updateBook(book);
        } catch (BookNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Book with ID " + book.getId() + " updated"));
    }
}