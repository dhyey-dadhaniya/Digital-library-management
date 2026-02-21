package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book save(Book book) {
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies() != null ? book.getTotalCopies() : 0);
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Optional<Book> update(Long id, Book updated) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setAuthor(updated.getAuthor());
                    existing.setIsbn(updated.getIsbn());
                    existing.setCategory(updated.getCategory());
                    existing.setPublisher(updated.getPublisher());
                    existing.setPublishedYear(updated.getPublishedYear());
                    existing.setTotalCopies(updated.getTotalCopies());
                    existing.setAvailableCopies(updated.getAvailableCopies() != null
                            ? updated.getAvailableCopies() : existing.getAvailableCopies());
                    return bookRepository.save(existing);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional(readOnly = true)
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Transactional(readOnly = true)
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Transactional(readOnly = true)
    public List<Book> findByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Book> findAvailable() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }
}
