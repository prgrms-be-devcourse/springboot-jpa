package com.devcourse.mission.domain.item.service;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.customer.service.CustomerService;
import com.devcourse.mission.domain.item.entity.Item;
import com.devcourse.mission.domain.order.service.OrderService;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    private Customer customer;

    private OrderItem orderItem;

    private Item item;

    @BeforeEach
    void setUpDummyData() {
        long customerId = customerService.save("박현서", "서울", 25);
        customer = customerService.getById(customerId);

        long itemId = itemService.save("책", 10000, 10);
        item = itemService.getById(itemId);

        orderItem = OrderItem.createOrderItem(item, 2);
    }

    @Test
    @DisplayName("상품과 관련된 주문이 있을 시 상품은 삭제되지 않고 예외에 발생한다.")
    void delete_exception() {
        // given
        orderService.order(customer.getId(), List.of(orderItem));

        // when & then
        assertThatThrownBy(() -> itemService.deleteById(item.getId()))
                .isExactlyInstanceOf(IllegalStateException.class);
    }
}