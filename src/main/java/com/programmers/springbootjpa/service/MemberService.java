package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.dto.request.CreateRequestDto;
import com.programmers.springbootjpa.entity.Member;
import com.programmers.springbootjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(CreateRequestDto createRequestDto) {
        Member member = createRequestDto.toEntity();
        memberRepository.save(member);
    }

}
