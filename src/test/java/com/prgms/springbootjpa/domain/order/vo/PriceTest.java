package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.PRICE_RANGE_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidPriceRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    @DisplayName("Price 생성 성공")
    void testCreatePriceSuccess() {
        //Given, When
        var price = new Price(7000);

        //Then
        assertThat(price).isNotNull();
    }

    @Test
    @DisplayName("가격이 한도보다 낮아, 생성 실패")
    void testCreatePriceFailBecauseMinPrice() {
        //Given, When, Then
        assertThatThrownBy(() -> new Price(999))
            .isInstanceOf(InvalidPriceRangeException.class)
            .hasMessageContaining(PRICE_RANGE_EXP_MSG.getMessage());
    }

    @Test
    @DisplayName("가격이 한도보다 높아, 생성 실패")
    void testCreatePriceFailBecauseMaxPrice() {
        //Given, When, Then
        assertThatThrownBy(() -> new Price(1000001))
            .isInstanceOf(InvalidPriceRangeException.class)
            .hasMessageContaining(PRICE_RANGE_EXP_MSG.getMessage());
    }
}