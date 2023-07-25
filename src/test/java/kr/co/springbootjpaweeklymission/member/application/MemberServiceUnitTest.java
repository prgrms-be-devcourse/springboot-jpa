package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 유저_서비스_주입_검증() {
        // Then
        assertThat(memberService).isNotNull();
    }

    @Test
    void 등록할_유저의_이메일_중복() {
        // Given
        final MemberCreatorRequest creator = MemberCreatorFactory.createMemberCreatorRequest();
        given(memberRepository.existsByEmail(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_EMAIL.getMessage());
    }

    @Test
    void 등록할_유저의_전화번호_중복() {
        // Given
        final MemberCreatorRequest creator = MemberCreatorFactory.createMemberCreatorRequest();
        given(memberRepository.existsByCellPhone(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_CELL_PHONE.getMessage());
    }

    @Test
    void 유저를_등록() {
        // Given
        final MemberCreatorRequest creator = MemberCreatorFactory.createMemberCreatorRequest();
        given(memberRepository.save(any(Member.class))).willAnswer(MemberServiceUnitTest::reflectionMemberId);

        // When
        final Long actual = memberService.createMember(creator);

        // Then
        assertThat(actual).isNotNull();
    }

    private static Object reflectionMemberId(InvocationOnMock invocation)
            throws NoSuchFieldException, IllegalAccessException {
        final Member member = invocation.getArgument(0);
        final Field memberId = Member.class.getDeclaredField("memberId");
        memberId.setAccessible(true);
        memberId.set(member, 1L);
        return member;
    }
}
