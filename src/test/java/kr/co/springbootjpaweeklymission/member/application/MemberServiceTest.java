package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.request.MemberPutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberCreatorFactory.createMember();
        memberRepository.save(member);
    }

    @DisplayName("특정 Id에 해당하는 회원 수정하는 테스트")
    @Test
    void updateMember_test() {
        // Given
        final MemberPutRequest putRequest = MemberCreatorFactory.createMemberPutRequest("updateEmail", "010-7777-7777");

        // When
        Long memberId = memberService.updateMember(member.getMemberId(), putRequest);
        final Member actual = memberRepository.findById(member.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));

        // Then
        assertThat(actual.getMemberId()).isEqualTo(memberId);
        assertThat(actual.getEmail()).isEqualTo("updateEmail");
        assertThat(actual.getCellPhone()).isEqualTo("010-7777-7777");
    }

    @DisplayName("특정 Id에 해당하는 회원 삭제하는 테스트")
    @Test
    void deleteMember_test() {
        // When
        Long actual = memberService.deleteMember(member.getMemberId());

        // Then
        assertThat(actual).isEqualTo(member.getMemberId());
        assertThat(memberRepository.existsById(actual)).isFalse();
    }
}
