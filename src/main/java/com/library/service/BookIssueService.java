package com.library.service;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.Member;
import com.library.repository.BookIssueRepository;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookIssueService {

    private final BookIssueRepository bookIssueRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BookIssueService(BookIssueRepository bookIssueRepository,
                            BookRepository bookRepository,
                            MemberRepository memberRepository) {
        this.bookIssueRepository = bookIssueRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<BookIssue> findAll() {
        return bookIssueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<BookIssue> findById(Long id) {
        return bookIssueRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<BookIssue> findByMemberId(Long memberId) {
        return bookIssueRepository.findByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<BookIssue> findByBookId(Long bookId) {
        return bookIssueRepository.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public List<BookIssue> findActiveIssues() {
        return bookIssueRepository.findByReturned(false);
    }

    @Transactional
    public BookIssue issueBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
        if (book.getAvailableCopies() == null || book.getAvailableCopies() < 1) {
            throw new IllegalStateException("No copies available for book: " + book.getTitle());
        }
        if (bookIssueRepository.findByBookIdAndMemberIdAndReturnedFalse(bookId, memberId).isPresent()) {
            throw new IllegalStateException("Member already has this book issued.");
        }
        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setMember(member);
        issue.setIssueDate(LocalDate.now());
        issue.setReturned(false);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        return bookIssueRepository.save(issue);
    }

    @Transactional
    public BookIssue returnBook(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue record not found: " + issueId));
        if (Boolean.TRUE.equals(issue.getReturned())) {
            throw new IllegalStateException("Book already returned.");
        }
        issue.setReturned(true);
        issue.setReturnDate(LocalDate.now());
        Book book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        return bookIssueRepository.save(issue);
    }

    @Transactional(readOnly = true)
    public Optional<BookIssue> findActiveIssue(Long bookId, Long memberId) {
        return bookIssueRepository.findByBookIdAndMemberIdAndReturnedFalse(bookId, memberId);
    }
}
