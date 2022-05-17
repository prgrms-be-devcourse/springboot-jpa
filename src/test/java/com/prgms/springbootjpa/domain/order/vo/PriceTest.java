package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.PRICE_RANGE_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidPriceRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 5000, 1000000})
    @DisplayName("Price 생성 성공")
    void testCreatePriceSuccess(int price) {
        //given
        //when
        var newPrice = new Price(price);

        //Then
        assertThat(newPrice).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 1000001})
    @DisplayName("가격이 범위 미만 또는 초과라 생성 실패")
    void testCreatePriceFailBecauseInvalidRange(int price) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(InvalidPriceRangeException.class)
            .hasMessageContaining(PRICE_RANGE_EXP_MSG.getMessage());
    }
}