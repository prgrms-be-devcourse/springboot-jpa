package com.prgms.springbootjpa.domain.customer;

import static com.prgms.springbootjpa.exception.ExceptionMessage.CUSTOMER_FIRST_NAME_FORMAT_EXP_MSG;
import static com.prgms.springbootjpa.exception.ExceptionMessage.CUSTOMER_LAST_NAME_FORMAT_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("이름 정상 생성")
    void testCreateSuccess() {
        //Given
        //When
        var name = new Name("eunhyuk", "bahng");

        //Then
        assertThat(name).isNotNull();
    }

    @Test
    @DisplayName("firstName 검증 실패")
    void testCreateFailBecauseFirstName() {
        //Given
        //When
        //Then
        assertThatThrownBy(() -> new Name("toooooooo long", "bahng"))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(CUSTOMER_FIRST_NAME_FORMAT_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("lastName 검증 실패")
    void testCreateFailBecauseLastName() {
        //Given
        //When
        //Then
        assertThatThrownBy(() -> new Name("eunhyuk", "tooooooo long"))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(CUSTOMER_LAST_NAME_FORMAT_EXP_MSG.getMessage());
    }
}