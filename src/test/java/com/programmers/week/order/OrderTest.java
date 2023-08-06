package com.programmers.week.order;

import com.programmers.week.order.domain.Order;
import com.programmers.week.order.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderTest {

    @DisplayName("올바른 주문 상태와 메모를 입력하여 수정할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"SUCCESS|메모메모|CANCELLED|수정된메모메모", "CANCELLED|메모입니다|SUCCESS|수정된메모입니다"}, delimiter = '|')
    void changeOrderTest(OrderStatus orderStatus, String memo, OrderStatus newOrderStatus, String newMemo) {
        Order order = new Order(orderStatus, memo);
        order.changeOrder(newOrderStatus, newMemo);

        assertAll(
                () -> assertThat(order.getOrderStatus()).isEqualTo(newOrderStatus),
                () -> assertThat(order.getMemo()).isEqualTo(newMemo)
        );

    }

}
