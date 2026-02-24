package com.library.controller;

import com.library.entity.Member;
import com.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // POST /api/members/register - Register new member
    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@Valid @RequestBody Member member) {
        if (memberService.existsByEmail(member.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Member saved = memberService.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // POST /api/members/login - Login
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().build();
        }
        return memberService.login(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // GET /api/members/admin - Get all members (Admin)
    @GetMapping("/admin")
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }

    // GET /api/members/admin/{id} - Get member by ID (Admin)
    @GetMapping("/admin/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/members/admin/{id} - Update member (Admin)
    @PutMapping("/admin/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member member) {
        return memberService.update(id, member)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/members/admin/{id} - Delete member (Admin)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (memberService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
