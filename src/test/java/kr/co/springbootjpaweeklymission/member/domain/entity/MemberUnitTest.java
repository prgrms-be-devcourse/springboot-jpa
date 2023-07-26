package kr.co.springbootjpaweeklymission.member.domain.entity;

import jakarta.validation.ConstraintViolationException;
import kr.co.springbootjpaweeklymission.member.domain.model.Address;
import kr.co.springbootjpaweeklymission.member.domain.model.MemberCreatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberUnitTest {
    @Autowired
    private TestEntityManager testEntityManager;

    private Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("000도 00시")
                .build();
    }

    @ParameterizedTest
    @CsvSource(value = {"example.com", "@gmail.com", "example@", "\\ "})
    @DisplayName(value = "이메일이 example@domainName.domainTop 형식을 따르지 않음")
    void email_validation_constraintViolationException_test(String email) {
        // Given
        final Member member = MemberCreatorFactory.createMember(email, "010-0000-0000", address);

        // When, Then
        assertThatThrownBy(() -> testEntityManager.persistAndFlush(member))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("핸드폰번호가 000-0000-0000 형식을 따르지 않음")
    @ParameterizedTest
    @CsvSource(value = {"010-12-5678", "01012345678", "0", "\\ ", "030-1234-1234"})
    void cellPhone_validation_constraintViolationException_test(String cellPhone) {
        // Given
        final Member member = MemberCreatorFactory.createMember("example@domain.top", cellPhone, address);

        // When, Then
        assertThatThrownBy(() -> testEntityManager.persistAndFlush(member))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
