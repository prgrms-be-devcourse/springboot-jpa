package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.QUANTITY_RANGE_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidQuantityRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    @DisplayName("Quantity 생성 성공")
    void testCreateQuantitySuccess() {
        //Given, When
        var quantity = new Quantity(77);

        //Then
        assertThat(quantity).isNotNull();
    }

    @Test
    @DisplayName("수량이 너무 적어, 생성 실패")
    void testCreateQuantityFailBecauseMinQuantity() {
        //Given, When, Then
        assertThatThrownBy(() -> new Quantity(0))
            .isInstanceOf(InvalidQuantityRangeException.class)
            .hasMessageContaining(QUANTITY_RANGE_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("수량이 너무 많아, 생성 실패")
    void testCreateQuantityFailBecauseMaxQuantity() {
        //Given, When, Then
        assertThatThrownBy(() -> new Quantity(1001))
            .isInstanceOf(InvalidQuantityRangeException.class)
            .hasMessageContaining(QUANTITY_RANGE_EXP_MSG.getMessage());
    }
}