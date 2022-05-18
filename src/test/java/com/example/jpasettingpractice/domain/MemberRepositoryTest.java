package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();
        member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        member.setAge(29);
        member.setAddress("daegu");
        member.setDescription("hi");
        memberRepository.save(member);
    }

    @Test
    void FIND_BY_ID_TEST() {
        // Given
        Long id = member.getId();

        // When
        Optional<Member> entity = memberRepository.findById(id);

        // Then
        assertThat(entity).contains(member);
    }

    @Test
    void FIND_ALL_TEST() {
        // Given // When
        List<Member> list = memberRepository.findAll();

        // Then
        assertThat(list).hasSize(1);
    }

    @Test
    void UPDATE_TEST() {
        // Given
        member.setName("brandon");

        // When
        memberRepository.save(member);

        // Then
        Optional<Member> updated = memberRepository.findById(member.getId());
        assertThat(updated).contains(member);
    }

    @Test
    void DELETE_BY_ID_TEST() {
        // Given
        Long id = member.getId();

        // When
        memberRepository.deleteById(id);

        // Then
        Optional<Member> byId = memberRepository.findById(id);
        assertThat(byId).isEmpty();
    }

    @Test
    void DELETE_BY_ENTITY_TEST() {
        // Given
        Long id = member.getId();
        
        // When
        memberRepository.delete(member);

        // Then
        Optional<Member> byId = memberRepository.findById(id);
        assertThat(byId).isEmpty();
    }
}