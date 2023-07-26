package kr.co.springbootjpaweeklymission.member.domain.repository;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("유저 레포지토리 주입 검증")
    @Test
    void memberRepositoryBeanRegisterTest() {
        // Then
        assertThat(memberRepository).isNotNull();
    }

    @DisplayName("유저 등록")
    @Test
    void memberRegisterTest() {
        // When
        final Member actual = memberRepository.save(member);

        // Then
        assertThat(actual.getMemberId()).isNotNull();
    }

    @DisplayName("유저 주소가 잘 등록되는지 검증")
    @Test
    void memberAddressRegisterTest() {
        // When
        final Member actual = memberRepository.save(member);

        // Then
        assertThat(actual.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(actual.getAddress().getDetail()).isEqualTo(member.getAddress().getDetail());
    }
}
