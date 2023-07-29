package com.programmers.jpa.repository;

import com.programmers.jpa.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("성공: member 저장")
    void save() {
        //given
        Member member = new Member("firstName", "lastName");

        //when
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("성공: member 조회 - 1차 캐시에서 조회")
    void find() {
        //given
        Member member = new Member("firstName", "lastName");
        memberRepository.save(member);

        //when
        Optional<Member> optionalMember = memberRepository.findById(member.getId());

        //then
        assertThat(optionalMember).isNotEmpty();
        Member findMember = optionalMember.get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("성공: member 조회 - 데이터 베이스에서 조회")
    void find_ClearPersistenceContext() {
        //given
        Member member = new Member("firstName", "lastName");
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        Optional<Member> optionalMember = memberRepository.findById(member.getId());

        //then
        Member findMember = optionalMember.get();
        assertThat(findMember).isNotEqualTo(member);
        assertThat(findMember).usingRecursiveComparison().isEqualTo(member);
    }

    @Test
    @DisplayName("성공: member 업데이트")
    void update() {
        //given
        Member member = new Member("firstName", "lastName");
        memberRepository.save(member);
        String updateFirstName = "updateFirstName";
        String updateLastName = "updateLastName";

        //when
        member.update(updateFirstName, updateLastName);

        //then
        em.flush();
        em.clear();
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember.getFirstName()).isEqualTo(updateFirstName);
        assertThat(findMember.getLastName()).isEqualTo(updateLastName);
    }

    @Test
    @DisplayName("성공: member 삭제")
    void delete() {
        //given
        Member member = new Member("firstName", "lastName");
        memberRepository.save(member);

        //when
        memberRepository.delete(member);

        //then
        em.flush();
        em.clear();
        Optional<Member> optionalMember = memberRepository.findById(member.getId());
        assertThat(optionalMember).isEmpty();
    }

}