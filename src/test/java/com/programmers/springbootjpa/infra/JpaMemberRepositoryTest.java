package com.programmers.springbootjpa.infra;

import com.programmers.springbootjpa.domain.Address;
import com.programmers.springbootjpa.domain.Member;
import com.programmers.springbootjpa.domain.MemberRepository;
import com.programmers.springbootjpa.ui.dto.MemberSaveRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void save() {
        // given
        Member member = Member.of(new MemberSaveRequest("kim", "1234", new Address("ilsan", "12345")));

        // when
        memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    void update() {
        // given
        Member member = Member.of(new MemberSaveRequest("kim", "1234", new Address("ilsan", "12345")));
        memberRepository.save(member);
        Member findMember = memberRepository.findById(member.getId()).get();

        // when
        findMember.changeName("park");

        // then
        entityManager.flush();
        entityManager.clear();

        Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getName()).isEqualTo("park");
    }

    @Test
    void delete() {
        // given
        Member member = Member.of(new MemberSaveRequest("kim", "1234", new Address("ilsan", "12345")));
        memberRepository.save(member);

        // when
        memberRepository.delete(member.getId());

        // then
        assertThat(memberRepository.findById(member.getId())).isEqualTo(Optional.empty());
    }
}
