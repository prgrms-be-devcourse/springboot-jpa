package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.main.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long memberId);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    int deleteById(Long memberId);

}
