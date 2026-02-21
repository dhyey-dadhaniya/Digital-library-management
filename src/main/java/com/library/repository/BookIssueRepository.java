package com.library.repository;

import com.library.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {

    List<BookIssue> findByMemberId(Long memberId);

    List<BookIssue> findByBookId(Long bookId);

    List<BookIssue> findByReturned(Boolean returned);

    Optional<BookIssue> findByBookIdAndMemberIdAndReturnedFalse(Long bookId, Long memberId);
}
