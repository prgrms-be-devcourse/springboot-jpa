package com.pgms.jpa.mission3;

import com.pgms.jpa.domain.item.Item;
import com.pgms.jpa.domain.item.ItemRepository;
import com.pgms.jpa.domain.order.Order;
import com.pgms.jpa.domain.order.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class relationMappingTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderService orderService;

    @Transactional
    @Test
    @DisplayName("order를 생성하면, order-orderItem-item이 연결된다.")
    void relationTest() {
        // given
        Item item = new Item("이펙티브 자바", 31400, 25);
        Long savedItemId = itemRepository.save(item);

        // when
        Long orderId = orderService.createOrder(savedItemId, 5);

        Order findOrder = orderService.findOne(orderId);

        // then
        Assertions.assertThat(findOrder.getOrderItems().get(0).getItem()).isEqualTo(item);
    }
}
