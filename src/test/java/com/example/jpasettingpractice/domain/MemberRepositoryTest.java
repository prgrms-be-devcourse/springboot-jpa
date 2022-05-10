package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void SAVE_TEST() {
        // Given
        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        member.setAge(29);
        member.setAddress("daegu");
        member.setDescription("hi");

        // When
        Member save = memberRepository.save(member);

        // Then
        assertThat(save).isEqualTo(member);
    }

    @Test
    @Transactional
    void FIND_BY_ID_TEST() {
        // Given
        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        member.setAge(29);
        member.setAddress("daegu");
        member.setDescription("hi");

        // When
        memberRepository.save(member);

        // Then
        Optional<Member> entity = memberRepository.findById(member.getId());
        assertThat(entity).contains(member);
    }

    @Test
    @Transactional
    void FIND_ALL_TEST() {
        // Given
        Member member1 = new Member();
        member1.setName("changho");
        member1.setNickName("lee");
        member1.setAge(29);
        member1.setAddress("daegu");
        member1.setDescription("hi");

        Member member2 = new Member();
        member2.setName("brandon");
        member2.setNickName("lee");
        member2.setAge(29);
        member2.setAddress("elsewhere");
        member2.setDescription("hi");

        // When
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Then
        var list = memberRepository.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    @Transactional
    void UPDATE_TEST() {
        // Given
        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        memberRepository.save(member);

        // When
        var byId = memberRepository.findById(member.getId());
        assertThat(byId).isPresent();
        byId.get().setName("brandon");

        // Then
        var updated = memberRepository.findById(byId.get().getId());
        assertThat(updated).contains(member);
    }

    @Test
    @Transactional
    void DELETE_BY_ID_TEST() {
        // Given
        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        memberRepository.save(member);

        // When
        memberRepository.deleteById(member.getId());

        // Then
        var all = memberRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @Transactional
    void DELETE_BY_ENTITY_TEST() {
        // Given
        Member member = new Member();
        member.setName("changho");
        member.setNickName("lee");
        memberRepository.save(member);

        // When
        memberRepository.delete(member);

        // Then
        var all = memberRepository.findAll();
        assertThat(all).isEmpty();
    }
}