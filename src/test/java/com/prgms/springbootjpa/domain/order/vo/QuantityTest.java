package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.QUANTITY_RANGE_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.exception.InvalidQuantityRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 50, 1000})
    @DisplayName("Quantity 생성 성공")
    void testCreateQuantitySuccess(int quantity) {
        //Given, When
        var newQuantity = new Quantity(quantity);

        //Then
        assertThat(newQuantity).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1001})
    @DisplayName("수량 범위 미만, 범위 초과라 실패")
    void testCreateQuantityFailBecauseInvalidRange(int quantity) {
        //Given, When, Then
        assertThatThrownBy(() -> new Quantity(0))
            .isInstanceOf(InvalidQuantityRangeException.class)
            .hasMessageContaining(QUANTITY_RANGE_EXP_MSG.getMessage());
    }
}
