package com.prgrms.m3.service;

import com.prgrms.m3.domain.*;
import com.prgrms.m3.repository.ItemRepository;
import com.prgrms.m3.repository.MemberRepository;
import com.prgrms.m3.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class OrderServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    private Member member;
    private Car car;
    private OrderItem orderItem;

    private static final int STOCK_QUANTITY = 5;
    private static final int ORDER_QUANTITY = 3;

    @BeforeEach
    void setup() {
        member = memberRepository.save(new Member("kiseo", "k", 26, "카타르"));
        car = itemRepository.save(new Car(100, 20000, STOCK_QUANTITY));
    }

    @Test
    @DisplayName("주문 수량이 상품의 재고보다 많을 경우 예외가 발생한다.")
    void 주문_실패() {
        assertThrows(RuntimeException.class, () -> OrderItem.createOrderItem(car, 6));
    }

    @Test
    @DisplayName("주문이 접수될 때 상품의 재고가 줄어든다.")
    void 주문_재고감소() {
        //given
        OrderItem orderItem = OrderItem.createOrderItem(car, ORDER_QUANTITY);
        Order order = Order.createOrder(member, orderItem);

        //when
        Order savedOrder = orderRepository.save(order);
        Item item = savedOrder.getOrderItems().get(0).getItem();

        //then
        assertThat(item.getStockQuantity()).isEqualTo(STOCK_QUANTITY - ORDER_QUANTITY);
    }

    @Test
    @DisplayName("주문을 취소할 경우 줄었던 상품의 재고가 원상복구된다.")
    void 주문_취소() {
        //given
        OrderItem orderItem = OrderItem.createOrderItem(car, ORDER_QUANTITY);
        Order order = Order.createOrder(member, orderItem);

        Order savedOrder = orderRepository.save(order);
        Item item = savedOrder.getOrderItems().get(0).getItem();
        assertThat(item.getStockQuantity()).isEqualTo(STOCK_QUANTITY - ORDER_QUANTITY);

        //when
        savedOrder.cancelOrder();

        //then
        assertThat(item.getStockQuantity()).isEqualTo(STOCK_QUANTITY);
    }

    @Test
    @DisplayName("주문이 접수될 때 Member, OrderItem 정보와 함께 저장된다.")
    @Commit
    void 주문_성공() {
        //given
        OrderItem orderItem = OrderItem.createOrderItem(car, ORDER_QUANTITY);
        Order order = Order.createOrder(member, orderItem);

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        assertEquals(member, savedOrder.getMember());
        assertEquals(orderItem, savedOrder.getOrderItems().get(0));
    }

    @Test
    @DisplayName("주문한 상품의 개수를 변경할 수 있다.")
    void 주문_수정() {
        //given
        OrderItem orderItem = OrderItem.createOrderItem(car, ORDER_QUANTITY);
        Order order = Order.createOrder(member, orderItem);

        Order savedOrder = orderRepository.save(order);

        Item item = savedOrder.getOrderItems().get(0).getItem();
        assertThat(item.getStockQuantity()).isEqualTo(STOCK_QUANTITY - ORDER_QUANTITY);

        //when
        int addedQuantity = 1;
        OrderItem orderedItem = savedOrder.getOrderItems().get(0);
        orderedItem.increaseQuantity(addedQuantity);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(STOCK_QUANTITY - ORDER_QUANTITY - addedQuantity);
    }
}