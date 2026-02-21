package com.library.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueBookRequest {
    private Long bookId;
    private Long memberId;

    @JsonCreator
    public IssueBookRequest(@JsonProperty("bookId") Object bookId, 
                           @JsonProperty("memberId") Object memberId) {
        this.bookId = extractLong(bookId);
        this.memberId = extractLong(memberId);
    }

    private Long extractLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
