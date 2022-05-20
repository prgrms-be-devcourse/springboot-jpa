package com.kdt.exercise.member.service;

import com.kdt.exercise.domain.order.Member;
import com.kdt.exercise.domain.order.MemberRepository;
import com.kdt.exercise.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long saveMember(MemberDto memberDto) {
        Member entity = memberRepository.save(convert(memberDto));
        return entity.getId();
    }


    public Long updateMember(Long id, MemberDto memberDto) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("회원정보를 찾을 수 없습니다."));

        entity.setName(memberDto.getName());
        entity.setNickName(memberDto.getNickName());
        entity.setAge(memberDto.getAge());
        entity.setAddress(memberDto.getAddress());
        entity.setDescription(memberDto.getDescription());

        return entity.getId();
    }

    public MemberDto getMember(Long id) {
        return memberRepository.findById(id)
                .map(it -> MemberDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .nickName(it.getNickName())
                        .age(it.getAge())
                        .address(it.getAddress())
                        .description(it.getDescription())
                        .build())
                .orElseThrow(() -> new NoSuchElementException("회원정보를 찾을 수 없습니다."));
    }

    private Member convert(MemberDto memberDto) {
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setNickName(memberDto.getNickName());
        member.setAge(memberDto.getAge());
        member.setAddress(memberDto.getAddress());
        member.setDescription(memberDto.getDescription());

        return member;
    }

}