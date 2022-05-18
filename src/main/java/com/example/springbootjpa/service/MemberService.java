package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.main.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member createMember(Member member);

    Optional<Member> getMemberById(Long memberId);

    Optional<Member> getMemberByEmail(String email);

    List<Member> getAllMembers();

    void deleteMemberById(Long memberId);

}
