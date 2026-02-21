package com.library.controller;

import com.library.entity.BookIssue;
import com.library.service.BookIssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
public class BookIssueController {

    private final BookIssueService bookIssueService;

    public BookIssueController(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    // POST /api/issues/issue - Issue a book to a member (Admin)
    @PostMapping("/issue")
    public ResponseEntity<?> issueBook(@RequestBody IssueBookRequest request) {
        if (request.getBookId() == null || request.getMemberId() == null) {
            throw new IllegalArgumentException("bookId and memberId are required and must be valid numbers");
        }
        
        BookIssue issue = bookIssueService.issueBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    // PUT /api/issues/return/{id} - Return a book (calc fine) (Admin)
    @PutMapping("/return/{id}")
    public ResponseEntity<BookIssue> returnBook(@PathVariable Long id) {
        try {
            BookIssue returned = bookIssueService.returnBook(id);
            return ResponseEntity.ok(returned);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/issues/member/{id} - View member's borrow history (User)
    @GetMapping("/member/{id}")
    public List<BookIssue> getMemberBorrowHistory(@PathVariable Long id) {
        return bookIssueService.findByMemberId(id);
    }

    // GET /api/issues/{id} - Get issue details (User)
    @GetMapping("/{id}")
    public ResponseEntity<BookIssue> getIssueById(@PathVariable Long id) {
        return bookIssueService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/issues/admin/all - All issue records (Admin)
    @GetMapping("/admin/all")
    public List<BookIssue> getAllIssues() {
        return bookIssueService.findAll();
    }

    // GET /api/issues/admin/current - Currently issued books (Admin)
    @GetMapping("/admin/current")
    public List<BookIssue> getCurrentIssues() {
        return bookIssueService.findActiveIssues();
    }
}
