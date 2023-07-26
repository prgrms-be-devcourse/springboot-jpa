package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import kr.co.springbootjpaweeklymission.member.dto.MemberReadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    private MemberCreatorRequest creator;

    @BeforeEach
    void setUp() {
        creator = MemberCreatorFactory.createMemberCreatorRequest();
    }

    @DisplayName("유저 서비스 주입 검증")
    @Test
    void member_service_bean_test() {
        // Then
        assertThat(memberService).isNotNull();
    }

    @DisplayName("등록할 유저의 이메일이 중복됨")
    @Test
    void email_duplicate_exception_test() {
        // Given
        given(memberRepository.existsByEmail(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_EMAIL.getMessage());
    }

    @DisplayName("등록할 유저의 핸드폰 번호가 중복됨")
    @Test
    void cellPhone_duplicateException_test() {
        // Given
        given(memberRepository.existsByCellPhone(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_CELL_PHONE.getMessage());
    }

    @DisplayName("유저를 등록")
    @Test
    void member_register_test() {
        // Given
        given(memberRepository.save(any(Member.class))).willAnswer(MemberServiceUnitTest::getMemberId);

        // When
        final Long actual = memberService.createMember(creator);

        // Then
        assertThat(actual).isNotNull();
    }

    @DisplayName("이메일에 해당하는 회원을 조회한다")
    @Test
    void get_member() {
        // Given
        final Member member = MemberCreatorFactory.createMember();
        given(memberRepository.findByEmail(any(String.class))).willReturn(Optional.of(member));

        // When
        MemberReadResponse actual = memberService.getMember(member.getEmail());

        // Then
        assertThat(actual.email()).isEqualTo(member.getEmail());
        assertThat(actual.cellPhone()).isEqualTo(member.getCellPhone());
    }

    private static Object getMemberId(InvocationOnMock invocation)
            throws NoSuchFieldException, IllegalAccessException {
        final Member member = invocation.getArgument(0);
        final Field memberId = Member.class.getDeclaredField("memberId");
        memberId.setAccessible(true);
        memberId.set(member, 1L);

        return member;
    }
}
