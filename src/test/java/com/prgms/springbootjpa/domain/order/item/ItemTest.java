package com.prgms.springbootjpa.domain.order.item;


import static com.prgms.springbootjpa.exception.ExceptionMessage.NO_AVAILABLE_QUANTITY_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import com.prgms.springbootjpa.exception.NoAvailableQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    @DisplayName("물품 구매 가능")
    void testConsumeQuantitySuccess() {
        //Given
        var quantity = new Quantity(20);
        var car = new Car(new Price(1000), quantity, 200);

        var consumeQuantity = new Quantity(10);

        //When
        car.consumeQuantity(consumeQuantity);

        //Then
        assertThat(car.getStockQuantity()).isEqualTo(quantity.sub(consumeQuantity));
    }

    @Test
    @DisplayName("재고 부족")
    void testConsumeQuantityFailBecauseNoStockQuantity() {
        //Given
        var quantity = new Quantity(20);
        var car = new Car(new Price(1000), quantity, 200);

        var consumeQuantity = new Quantity(30);

        //When, Then
        assertThatThrownBy(() -> car.consumeQuantity(consumeQuantity))
            .isInstanceOf(NoAvailableQuantityException.class)
            .hasMessageContaining(NO_AVAILABLE_QUANTITY_EXP_MSG.getMessage());
    }
}