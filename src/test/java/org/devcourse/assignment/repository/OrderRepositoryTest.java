package org.devcourse.assignment.repository;

import org.devcourse.assignment.domain.customer.Customer;
import org.devcourse.assignment.domain.order.Item;
import org.devcourse.assignment.domain.order.Order;
import org.devcourse.assignment.domain.order.OrderItem;
import org.devcourse.assignment.domain.order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @DisplayName("주문을 생성할 수 있다.")
    @Test
    void save() {
        // given
        Customer customer = createCustomer("의진", "jinny");
        customerRepository.save(customer);

        Item item = createItem("크리스마스 기모 티셔츠", 24_000);
        itemRepository.save(item);

        OrderItem orderItem = createOrderItem(item, 1);

        Order order = Order.builder()
                .customer(customer)
                .build();

        order.addOrderItem(orderItem);

        // when
        Order saved = orderRepository.save(order);

        // then
        assertThat(saved.getOrderDateTime()).isNotNull();
        assertThat(saved.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(saved.getOrderItems()).hasSize(1);
        assertThat(saved.getCustomer()).isEqualTo(customer);
    }

    private static Item createItem(String name, Integer price) {
        return Item.builder()
                .name(name)
                .stockQuantity(100)
                .price(price)
                .build();
    }

    private static OrderItem createOrderItem(Item item, Integer quantity) {
        return OrderItem.builder()
                .item(item)
                .quantity(quantity)
                .build();
    }

    private static Customer createCustomer(String name, String nickName) {
        return Customer.builder()
                .name(name)
                .nickName(nickName)
                .description("안녕하세요:)")
                .build();
    }
}
