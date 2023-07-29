package com.springbootjpa.domain;

import com.springbootjpa.exception.InValidOrderItemException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {

    @Test
    void 재고수량_보다_많은_수를_주문하면_예외가_발생한다() {
        // given
        Customer customer = new Customer("이", "근우");
        Delivery delivery = new Delivery("경기도 고양시", "한화오벨리스크");
        Order order = new Order(customer, delivery);
        Item item = new Item("아이폰24", 10);

        OrderItem orderItem = new OrderItem(order, item);

        // when & then
        assertThatThrownBy(() -> orderItem.orderItem(15))
                .isInstanceOf(InValidOrderItemException.class)
                .hasMessage("재고보다 많은 수량을 주문할 수 없습니다.");
    }
}
