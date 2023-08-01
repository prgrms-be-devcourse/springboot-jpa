package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidCustomerException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    void 유효하지_않는_이름으로_변경하면_예외가_발생한다() {
        // given
        Customer customer = new Customer("이", "근우");
        String firstName = "이";
        String lastName = "가나다라마바";

        // when & then
        assertThatThrownBy(() -> customer.changeName(firstName, lastName))
                .isInstanceOf(InValidCustomerException.class)
                .hasMessage("성을 제외하고 이름은 5자를 초과할 수 없습니다.");
    }
}
