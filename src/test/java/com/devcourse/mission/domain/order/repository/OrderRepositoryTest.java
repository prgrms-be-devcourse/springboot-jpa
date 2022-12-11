package com.devcourse.mission.domain.order.repository;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.customer.repository.CustomerRepository;
import com.devcourse.mission.domain.item.entity.Item;
import com.devcourse.mission.domain.item.repository.ItemRepository;
import com.devcourse.mission.domain.order.entity.Order;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import com.devcourse.mission.domain.orderitem.repository.OrderItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {
        OrderRepository.class,
        CustomerRepository.class,
        ItemRepository.class,
        OrderItemRepository.class
})
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Customer customer;

    private OrderItem orderItem;

    private Item item;


    @BeforeEach
    void setUpDummyData() {
        customer = customerRepository.save(new Customer("박현서", "서울", 25));
        item = itemRepository.save(new Item("책", 10000, 10));
        orderItem = OrderItem.createOrderItem(item, 2);
    }

    @AfterEach
    void clear() {
        orderItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문 저장에 성공한다.")
    void save_item() {
        // when
        Order order = Order.createOrder(customer);
        order.addOrderItem(orderItem);

        Order savedOrder = orderRepository.saveAndFlush(order);
        OrderItem savedOrderItem = savedOrder.getOrderItems().get(0);

        // then
        assertThat(savedOrderItem)
                .hasFieldOrPropertyWithValue("totalPrice", 20000)
                .hasFieldOrPropertyWithValue("quantity", 2);
    }

    @Test
    @DisplayName("주문 검색에 성공한다.")
    void find_item_by_id() {
        // given
        Order order = Order.createOrder(customer);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);

        // when
        Order findOrder = orderRepository.joinCustomerOrderItemByOrderId(savedOrder.getId());

        // then
        Assertions.assertThat(findOrder)
                .usingRecursiveComparison()
                .isEqualTo(savedOrder);
    }

    @Test
    @DisplayName("주문 수정에 성공한다.")
    void update_item() {
        // given
        Order order = Order.createOrder(customer);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);

        // when
        savedOrder.getOrderItems()
                .forEach(orderItem -> orderItem.decreaseQuantity(1));

        Order findOrder = orderRepository.joinOrderItemByOrderId(order.getId());

        // then
        Assertions.assertThat(savedOrder)
                .usingRecursiveComparison()
                .isEqualTo(savedOrder);
    }

    @Test
    @DisplayName("주문 삭제에 성공한다.")
    void delete_item() {
        // given
        Order order = Order.createOrder(customer);
        order.addOrderItem(orderItem);
        Order savedOrder = orderRepository.saveAndFlush(order);

        // when
        orderRepository.deleteById(savedOrder.getId());
        boolean isDeleted = orderRepository.findById(savedOrder.getId()).isEmpty();

        // then
        assertThat(isDeleted).isTrue();
    }
}