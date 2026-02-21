package com.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        return ResponseEntity.ok(Map.of(
                "message", "Digital Library Management API",
                "endpoints", Map.of(
                        "books", "/api/books",
                        "members", "/api/members",
                        "issues", "/api/issues"
                )
        ));
    }
}
