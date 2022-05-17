package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NAME_LENGTH_EXP_MSG;
import static com.prgms.springbootjpa.exception.ExceptionMessage.NICK_NAME_LENGTH_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NickNameTest {

    @Test
    @DisplayName("닉네임 생성 성공")
    void testCreateNickNameSuccess() {
        //Given, When
        var nickName = new NickName("hello");

        //Then
        assertThat(nickName).isNotNull();
    }

    @Test
    @DisplayName("닉네임이 너무 짧아 생성 실패")
    void testCreateNickNameFailBecauseMinLength() {
        //Given, When, Then
        assertThatThrownBy(() -> new NickName(""))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NICK_NAME_LENGTH_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("닉네임이 너무 길어 생성 실패")
    void testCreateNickNameFailBecauseMaxLength() {
        //Given
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 31; ++i) {
            sb.append("a");
        }
        String nickName = sb.toString();

        //When, Then
        assertThatThrownBy(() -> new Name(nickName))
            .isInstanceOf(InvalidNameLengthException.class)
            .hasMessageContaining(NAME_LENGTH_EXP_MSG.getMessage());
    }
}