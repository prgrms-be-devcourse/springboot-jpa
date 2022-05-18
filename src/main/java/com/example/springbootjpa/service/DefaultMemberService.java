package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.main.Member;
import com.example.springbootjpa.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public DefaultMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
