package com.library.service;

import com.library.entity.Member;
import com.library.entity.Member.Role;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Member save(Member member) {
        if (member.getRole() == null) {
            member.setRole(Role.USER);
        }
        return memberRepository.save(member);
    }

    @Transactional
    public Optional<Member> update(Long id, Member updated) {
        return memberRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
                        existing.setPassword(updated.getPassword());
                    }
                    existing.setPhone(updated.getPhone());
                    existing.setAddress(updated.getAddress());
                    if (updated.getRole() != null) {
                        existing.setRole(updated.getRole());
                    }
                    return memberRepository.save(existing);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Member> findByRole(Role role) {
        return memberRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public Optional<Member> login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(member -> member.getPassword() != null && member.getPassword().equals(password));
    }
}
