package kr.co.springbootjpaweeklymission.member.domain.entity;

import jakarta.validation.ConstraintViolationException;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberUnitTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @ParameterizedTest
    @CsvSource(value = {"exampenaver.com", "@naver.com", "example@", "\\ "})
    @DisplayName(value = "이메일은 example@domainName.domainTop 형식을 따른다.")
    void 이메일이_유효한_형식인지_검증(String email) {
        // Given
        final Member member
                = MemberCreatorFactory.createMember(email, "010-0000-0000", "addressTest");

        // When, Then
        assertThatThrownBy(() -> testEntityManager.persistAndFlush(member))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
