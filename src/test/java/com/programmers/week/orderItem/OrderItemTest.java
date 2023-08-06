package com.programmers.week.orderItem;

import com.programmers.week.item.domain.Car;
import com.programmers.week.order.domain.Order;
import com.programmers.week.order.domain.OrderStatus;
import com.programmers.week.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderItemTest {

    @Test
    void orderItemCreateTest() {
        Order order = new Order(OrderStatus.SUCCESS, "메모메모메모");
        Car car = Car.of(1000, 10, 2000);
        OrderItem orderItem = new OrderItem(1L, order, car);

        assertAll(
                () -> assertThat(orderItem.getItem().getPrice()).isEqualTo(1000),
                () -> assertThat(orderItem.getItem().getStockQuantity()).isEqualTo(10),
                () -> assertThat(orderItem.getOrder().getOrderStatus()).isEqualTo(OrderStatus.SUCCESS),
                () -> assertThat(orderItem.getOrder().getMemo()).isEqualTo("메모메모메모")
        );
    }

}
