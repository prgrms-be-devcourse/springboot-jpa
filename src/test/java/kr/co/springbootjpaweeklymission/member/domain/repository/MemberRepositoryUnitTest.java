package kr.co.springbootjpaweeklymission.member.domain.repository;

import kr.co.springbootjpaweeklymission.global.config.JpaConfig;
import kr.co.springbootjpaweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
class MemberRepositoryUnitTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        member = MemberCreatorFactory.createMember();
        savedMember = memberRepository.save(member);
    }

    @DisplayName("유저 레포지토리 주입 검증")
    @Test
    void member_repository_bean_register_test() {
        // Then
        assertThat(memberRepository).isNotNull();
    }

    @DisplayName("유저 등록")
    @Test
    void member_register_test() {
        // Then
        assertThat(savedMember.getMemberId()).isNotNull();
    }

    @DisplayName("유저 주소가 잘 등록되는지 검증")
    @Test
    void member_address_register_test() {
        // Then
        assertThat(savedMember.getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(savedMember.getAddress().getDetail()).isEqualTo(member.getAddress().getDetail());
    }

    @DisplayName("회원 등록 시, 생성일이 자동 등록 된다.")
    @Test
    void member_createAt_register() {
        // Then
        assertThat(savedMember.getCreatedAt()).isEqualTo(LocalDate.now());
    }

    @DisplayName("이메일로 회원을 조회한다")
    @Test
    void find_member_by_id() {
        // When
        final Member actual = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));

        // Then
        assertThat(actual).isEqualTo(savedMember);
    }
}
