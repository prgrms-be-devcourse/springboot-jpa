package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("아이디로 멤버를 찾는다.")
    void FIND_BY_ID_TEST() {
        // Given
        Long id = member.getId();

        // When
        Optional<Member> entity = memberRepository.findById(id);

        // Then
        assertThat(entity).contains(member);
    }

    @Test
    @DisplayName("모든 멤버를 찾을 수 있다.")
    void FIND_ALL_TEST() {
        // Given // When
        List<Member> list = memberRepository.findAll();

        // Then
        assertThat(list).hasSize(1);
    }

    @Test
    @DisplayName("멤버 정보를 수정 할 수 있다.")
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
    @DisplayName("해당 아이디의 멤버 정보를 삭제 할 수 있다.")
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
    @DisplayName("해당 객체의 멤버를 삭제 할 수 있다.")
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