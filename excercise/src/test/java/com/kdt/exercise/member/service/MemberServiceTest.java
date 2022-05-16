package com.kdt.exercise.member.service;

import com.kdt.exercise.domain.order.Member;
import com.kdt.exercise.domain.order.MemberRepository;
import com.kdt.exercise.member.dto.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    private Long memberId;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        // Given
        MemberDto memberDto = MemberDto.builder()
                .id(1L)
                .name("kang")
                .nickName("wansu")
                .age(27)
                .address("jeju")
                .description("hello")
                .build();

        // When
        memberId = memberService.saveMember(memberDto);

        // Then
        Assertions.assertThat(memberId).isEqualTo(1L);
    }

    @Test
    void member_조회() {
        // Given
        Long id = memberId;

        // When
        MemberDto member = memberService.getMember(id);

        // Then
        assertThat(member.getId()).isNotNull();
    }

    @Test
    void member_수정() {
        // Given
        MemberDto memberDto = MemberDto.builder()
                .name("kang")
                .nickName("wansu")
                .age(27)
                .address("jeju")
                .description("hello")
                .build();

        // When
        Long id = memberService.updateMember(memberId, memberDto);

        // Then
        Member member = memberRepository.findById(id).get();
        assertThat(member.getNickName()).isEqualTo(memberDto.getNickName());
    }
}