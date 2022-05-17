package com.prgms.springbootjpa.domain.customer;

import static com.prgms.springbootjpa.exception.ExceptionMessage.CUSTOMER_FIRST_NAME_FORMAT_EXP_MSG;
import static com.prgms.springbootjpa.exception.ExceptionMessage.CUSTOMER_LAST_NAME_FORMAT_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    public static final String A = "a";

    @ParameterizedTest
    @CsvSource({"1, 1", "3, 3", "10, 5"})
    @DisplayName("이름 정상 생성")
    void testCreateSuccess(int firstLength, int lastLength) {
        //given, when
        var name = new Name(
            generateName(firstLength),
            generateName(lastLength)
        );

        //then
        assertThat(name).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 11})
    @DisplayName("firstName 검증 실패")
    void testCreateFailBecauseFirstName(int firstLength) {
        //given
        String firstName = generateName(firstLength);
        String lastName = generateName(3);

        //when
        //then
        assertThatThrownBy(() -> new Name(firstName, lastName))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(CUSTOMER_FIRST_NAME_FORMAT_EXP_MSG.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6})
    @DisplayName("lastName 검증 실패")
    void testCreateFailBecauseLastName(int lastLength) {
        //given
        String firstName = generateName(5);
        String lastName = generateName(lastLength);
        
        //when
        //then
        assertThatThrownBy(() -> new Name(firstName, lastName))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(CUSTOMER_LAST_NAME_FORMAT_EXP_MSG.getMessage());
    }

    private String generateName(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}