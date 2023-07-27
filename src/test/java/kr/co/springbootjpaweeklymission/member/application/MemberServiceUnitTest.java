package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.request.MemberPutRequest;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberDetailResponse;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberSimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

    private MemberPutRequest creator;
    private Member member;

    @BeforeEach
    void setUp() {
        creator = MemberCreatorFactory.createMemberPutRequest();
        member = MemberCreatorFactory.createMember();
    }

    @DisplayName("유저 서비스가 주입 됐는 지 검증")
    @Test
    void memberService_bean_test() {
        // Then
        assertThat(memberService).isNotNull();
    }

    @DisplayName("등록할 유저의 이메일이 중복되어 DuplicateException 예외가 발생")
    @Test
    void createMember_email_duplicateException_test() {
        // Given
        given(memberRepository.existsByEmail(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_EMAIL.getMessage());
    }

    @DisplayName("등록할 유저의 핸드폰 번호가 중복되어 DuplicateException 예외가 발생")
    @Test
    void createMember_cellPhone_duplicateException_test() {
        // Given
        given(memberRepository.existsByCellPhone(any(String.class))).willReturn(true);

        // When, Then
        assertThatThrownBy(() -> memberService.createMember(creator))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(ErrorResult.DUPLICATED_CELL_PHONE.getMessage());
    }

    @DisplayName("유저를 등록되는 지 테스트")
    @Test
    void createMember_test() {
        // Given
        given(memberRepository.save(any(Member.class))).willAnswer(MemberServiceUnitTest::getMemberId);

        // When
        final Long actual = memberService.createMember(creator);

        // Then
        assertThat(actual).isNotNull();
    }

    @DisplayName("해당 이메일과 일치하는 회원을 조회하는 테스트")
    @Test
    void getMember_test() {
        // Given
        given(memberRepository.findByEmail(any(String.class))).willReturn(Optional.of(member));

        // When
        MemberDetailResponse actual = memberService.getMember(member.getEmail());

        // Then
        assertThat(actual.email()).isEqualTo(member.getEmail());
        assertThat(actual.cellPhone()).isEqualTo(member.getCellPhone());
    }

    @DisplayName("모든 회원을 조회하는 테스트")
    @Test
    @CsvSource(value = {"", "ex2@naver.com", "ex3@naver.com"})
    void getMembers_test() {
        // Given
        final Member other = MemberCreatorFactory.createMember("other@naver.com", "010-1212-1212");
        List<Member> members = new ArrayList<>(List.of(member, other));
        given(memberRepository.findAll()).willReturn(members);

        // When
        final List<MemberSimpleResponse> actual = memberService.getMembers();

        // Then
        assertThat(actual).hasSize(2);
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
