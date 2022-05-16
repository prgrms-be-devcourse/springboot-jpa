package com.kdt.exercise.member.service;

import com.kdt.exercise.domain.order.Member;
import com.kdt.exercise.domain.order.MemberRepository;
import com.kdt.exercise.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
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
                .name("kanghonggu")
                .nickName("guppy.kang")
                .age(33)
                .address("seoul")
                .description("--")
                .build();

        // When
        memberId = memberService.saveMember(memberDto);

        // Then
        log.info("{}", memberId);
    }

    @Test
    void update_test() {
        // Given
        MemberDto memberDto = MemberDto.builder()
                .name("kanghonggu2")
                .nickName("guppy.kang2")
                .age(33)
                .address("seoul2")
                .description("--2")
                .build();

        // When
        Long id = memberService.updateMember(memberId, memberDto);

        // Then
        Member member = memberRepository.findById(id).get();
        assertThat(member.getNickName()).isEqualTo(memberDto.getNickName());
    }

    @Test
    void get_test() {
        // Given
        Long id = memberId;

        // When
        MemberDto member = memberService.getMember(id);

        // Then
        log.info("{}", member);
    }
}