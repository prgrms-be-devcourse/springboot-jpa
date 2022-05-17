package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NICK_NAME_LENGTH_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NickNameTest {

    public static final String A = "a";

    @ParameterizedTest
    @ValueSource(ints = {1, 15, 30})
    @DisplayName("닉네임 생성 성공")
    void testCreateNickNameSuccess(int length) {
        //given, when
        var nickName = new NickName(generateStringNickName(length));

        //then
        assertThat(nickName).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 31})
    @DisplayName("닉네임 길이 1미만 30초과 따라서 생성 실패")
    void testCreateNickNameFailBecauseInvalidLength(int length) {
        //given
        String name = generateStringNickName(length);

        //when
        //then
        assertThatThrownBy(() -> new NickName(name))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NICK_NAME_LENGTH_EXP_MSG.getMessage());
    }

    private String generateStringNickName(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}
