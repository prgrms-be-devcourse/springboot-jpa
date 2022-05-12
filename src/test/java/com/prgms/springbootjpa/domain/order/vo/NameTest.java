package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NAME_LENGTH_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("이름 생성 성공")
    void testCreateNameSuccess() {
        //Given
        //When
        var name = new Name("hello");

        //Then
        assertThat(name).isNotNull();
    }

    @Test
    @DisplayName("이름이 너무 짧아 생성 실패")
    void testCreateNameFailBecauseMinLength() {
        //Given, When, Then
        assertThatThrownBy(() -> new Name(""))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NAME_LENGTH_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("이름이 너무 길어 생성 실패")
    void testCreateNameFailBecauseMaxLength() {
        //Given
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 31; ++i) {
            sb.append("a");
        }
        String name = sb.toString();
        
        //When, Then
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NAME_LENGTH_EXP_MSG.getMessage());
    }
}