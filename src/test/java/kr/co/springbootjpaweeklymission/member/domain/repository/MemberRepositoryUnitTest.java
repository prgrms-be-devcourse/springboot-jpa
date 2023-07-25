package kr.co.springbootjpaweeklymission.member.domain.repository;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryUnitTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 유저_레포지토리_주입_검증() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    void 유저_등록() {
        // Given
        final Member member = MemberCreatorFactory.createMember();

        // When
        final Member actual = memberRepository.save(member);

        // Then
        assertThat(actual.getMemberId()).isEqualTo(1L);
    }
}
