package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidCustomerException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @Test
    void 유효하지_않는_이름을_생성하면_예외가_발생한다() {
        // given
        String firstName = "이";
        String lastName = "가나다라마바";

        // when & then
        assertThatThrownBy(() -> new Name(firstName, lastName))
                .isInstanceOf(InValidCustomerException.class)
                .hasMessage("성을 제외하고 이름은 5자를 초과할 수 없습니다.");
    }
}
