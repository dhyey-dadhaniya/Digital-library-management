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

    @GetMapping
    public List<BookIssue> getAllIssues(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) Boolean active) {
        if (memberId != null) {
            return bookIssueService.findByMemberId(memberId);
        }
        if (bookId != null) {
            return bookIssueService.findByBookId(bookId);
        }
        if (Boolean.TRUE.equals(active)) {
            return bookIssueService.findActiveIssues();
        }
        return bookIssueService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookIssue> getIssueById(@PathVariable Long id) {
        return bookIssueService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/issue")
    public ResponseEntity<BookIssue> issueBook(@RequestBody Map<String, Long> body) {
        Long bookId = body.get("bookId");
        Long memberId = body.get("memberId");
        if (bookId == null || memberId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            BookIssue issue = bookIssueService.issueBook(bookId, memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BookIssue> returnBook(@PathVariable Long id) {
        try {
            BookIssue returned = bookIssueService.returnBook(id);
            return ResponseEntity.ok(returned);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
