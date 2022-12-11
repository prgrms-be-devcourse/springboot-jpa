package com.prgms.springbootjpa.domain.order.item;


import static com.prgms.springbootjpa.exception.ExceptionMessage.NO_AVAILABLE_QUANTITY_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import com.prgms.springbootjpa.exception.NoAvailableQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car(
            new Price(1000),
            new Quantity(20),
            200
        );
    }

    @Test
    @DisplayName("물품 구매 가능")
    void testConsumeQuantitySuccess() {
        //given
        var stockQuantity = car.getStockQuantity();
        var consumeQuantity = new Quantity(10);

        //when
        car.consumeQuantity(consumeQuantity);

        //then
        assertThat(car.getStockQuantity()).isEqualTo(stockQuantity.sub(consumeQuantity));
    }

    @Test
    @DisplayName("재고 부족")
    void testConsumeQuantityFailBecauseNoStockQuantity() {
        //given
        var consumeQuantity = new Quantity(30);

        //when, then
        assertThatThrownBy(() -> car.consumeQuantity(consumeQuantity))
            .isInstanceOf(NoAvailableQuantityException.class)
            .hasMessageContaining(NO_AVAILABLE_QUANTITY_EXP_MSG.getMessage());
    }
}