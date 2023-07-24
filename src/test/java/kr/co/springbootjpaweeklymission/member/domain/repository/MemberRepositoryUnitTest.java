package kr.co.springbootjpaweeklymission.member.domain.repository;

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
}
