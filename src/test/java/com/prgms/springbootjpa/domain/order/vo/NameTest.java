package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NAME_LENGTH_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    public static final String A = "a";

    @ParameterizedTest
    @ValueSource(ints = {1, 15, 30})
    @DisplayName("이름 생성 성공")
    void testCreateNameSuccess(int length) {
        //given, when
        var name = new Name(generateStringName(length));

        //then
        assertThat(name).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 31})
    @DisplayName("이름이 길이가 1미만 30초과 따라서 생성 실패")
    void testCreateNameFailBecauseInvalidLength(int length) {
        //given
        String name = generateStringName(length);
        //when
        //then
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NAME_LENGTH_EXP_MSG.getMessage());
    }
    
    private String generateStringName(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}
