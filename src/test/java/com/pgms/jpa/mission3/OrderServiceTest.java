package com.pgms.jpa.mission3;

import com.pgms.jpa.domain.item.Item;
import com.pgms.jpa.domain.item.ItemRepository;
import com.pgms.jpa.domain.order.Order;
import com.pgms.jpa.domain.order.OrderStatus;
import com.pgms.jpa.domain.order.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Transactional
    @DisplayName("주문을 할 수 있다.")
    void createOrderTest() {
        //given
        Item item = new Item("phone", 10000, 3);
        itemRepository.save(item);

        //when
        Long orderId = orderService.createOrder(item.getId(), 2);
        Order findOrder = orderService.findOne(orderId);

        //then
        Assertions.assertThat(findOrder.getOrderItems()).hasSize(1);
        Assertions.assertThat(findOrder.getTotalPrice()).isEqualTo(20000);
        Assertions.assertThat(findOrder.getOrderItems().get(0).getItem().getQuantity()).isEqualTo(1);
        Assertions.assertThat(findOrder.getCreatedAt()).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("주문을 취소할 수 있다.")
    void cancelOrderTest() {
        //given
        Item item = new Item("phone", 10000, 3);
        itemRepository.save(item);

        Long orderId = orderService.createOrder(item.getId(), 2);
        Order findOrder = orderService.findOne(orderId);

        //when
        findOrder.cancelOrder();

        //then
        Assertions.assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        Assertions.assertThat(item.getQuantity()).isEqualTo(3);
    }
}