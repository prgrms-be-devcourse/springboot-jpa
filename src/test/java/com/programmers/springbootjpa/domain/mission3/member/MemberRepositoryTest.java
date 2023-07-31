package com.programmers.springbootjpa.domain.mission3.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("hyemin", "nick", 26, "서울특별시 성북구");
    }
    
    @DisplayName("회원을 저장한다")
    @Test
    void testSave() {
        //given
        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(savedMember.getAge()).isEqualTo(member.getAge());
        assertThat(savedMember.getAddress()).isEqualTo(member.getAddress());
    }

    @DisplayName("회원을 수정한다")
    @Test
    void TestUpdate() {
        //given
        Member savedMember = memberRepository.save(member);

        //when
        savedMember.updateName("min");
        savedMember.updateNickname("nikkk");
        savedMember.updateAge(22);
        savedMember.updateAddress("경기도");

        //then
        assertThat(savedMember.getName()).isEqualTo("min");
        assertThat(savedMember.getNickname()).isEqualTo("nikkk");
        assertThat(savedMember.getAge()).isEqualTo(22);
        assertThat(savedMember.getAddress()).isEqualTo("경기도");
    }

    @DisplayName("회원을 id로 조회한다")
    @Test
    void testFindById() {
        //given
        Member savedMember = memberRepository.save(member);

        //when
        Member result = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(result.getName()).isEqualTo(savedMember.getName());
        assertThat(result.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(result.getAge()).isEqualTo(savedMember.getAge());
        assertThat(result.getAddress()).isEqualTo(savedMember.getAddress());
    }

    @DisplayName("저장된 회원을 모두 조회한다")
    @Test
    void testFindAll() {
        //given
        memberRepository.save(member);

        Member member2 = new Member("jh", "jjj", 22, "경기도");
        memberRepository.save(member2);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).hasSize(2);
    }

    @DisplayName("회원을 삭제한다")
    @Test
    void testDelete() {
        //given
        Member savedMember = memberRepository.save(member);

        //when
        memberRepository.delete(savedMember);
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).isEmpty();
    }

    @DisplayName("저장된 회원을 모두 삭제한다")
    @Test
    void testDeleteAll() {
        //given
        memberRepository.save(member);

        Member member2 = new Member("jh", "jjj", 22, "경기도");
        memberRepository.save(member2);

        //when
        memberRepository.deleteAll();
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).isEmpty();
    }
}