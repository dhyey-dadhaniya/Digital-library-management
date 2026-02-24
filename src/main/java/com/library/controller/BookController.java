package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/books - Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    // GET /api/books/{id} - Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/books/available - Get available books only
    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.findAvailable();
    }

    // GET /api/books/search/title?title=X - Search by title
    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }

    // GET /api/books/search/author?author=X - Search by author
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.findByAuthor(author);
    }

    // GET /api/books/category/{category} - Browse by category
    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.findByCategory(category);
    }

    // POST /api/books/admin - Add a new book (Admin)
    @PostMapping("/admin")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book saved = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/books/admin/{id} - Update book (Admin)
    @PutMapping("/admin/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.update(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/books/admin/{id} - Delete book (Admin)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
