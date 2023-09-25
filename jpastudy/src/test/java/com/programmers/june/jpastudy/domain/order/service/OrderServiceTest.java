package com.programmers.june.jpastudy.domain.order.service;

import com.programmers.june.jpastudy.domain.customer.entity.Customer;
import com.programmers.june.jpastudy.domain.item.entity.Item;
import com.programmers.june.jpastudy.domain.order.entity.Order;
import com.programmers.june.jpastudy.domain.order_item.model.OrderStatus;
import com.programmers.june.jpastudy.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    Customer customer;
    Item item;

    @BeforeEach
    void setting() {
        customer = Customer.builder()
                .firstName("junhyuk")
                .lastName("choi")
                .build();

        item = Item.builder()
                .name("apple")
                .price(1000)
                .stockQuantity(10)
                .build();
    }

    @Test
    void 상품_주문() {
        // given
        int orderCount = 2;

        // when
        Long orderId = orderService.order(customer.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found!"));

        assertEquals(OrderStatus.OPENED, getOrder.getOrderStatus());    // 주문 상태 테스트
        assertEquals(1, getOrder.getOrderItems().size());   // 주문 수량 테스트
        assertEquals(8, item.getStockQuantity());   // 주문 수 만큼 제고가 감소 했는지 테스트
    }

    @Test
    public void 상품_주문_재고_수량_초과() {
        // given
        int orderCount = 11;

        // when
        // then
        assertThrows(RuntimeException.class, () -> orderService.order(customer.getId(), item.getId(), orderCount));
    }

    @Test
    public void 주문_취소() {
        // given
        int orderCount = 2;

        Long orderId = orderService.order(customer.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found!"));

        assertEquals(OrderStatus.CANCELLED, getOrder.getOrderStatus());
        assertEquals(10, item.getStockQuantity());  // 주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
    }
}