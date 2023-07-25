package kr.co.springbootjpaweeklymission.member.domain.repository;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryUnitTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberCreatorFactory.createMember();
    }

    @Test
    void 유저_레포지토리_주입_검증() {
        // Then
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void 유저_등록() {
        // When
        final Member actual = memberRepository.save(member);

        // Then
        assertThat(actual.getMemberId()).isNotNull();
    }

    @Test
    void 유저_주소가_잘_등록되는_지_검증() {
        // When
        final Member actual = memberRepository.save(member);

        // Then
        assertThat(actual.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(actual.getAddress().getDetail()).isEqualTo(member.getAddress().getDetail());
    }
}
