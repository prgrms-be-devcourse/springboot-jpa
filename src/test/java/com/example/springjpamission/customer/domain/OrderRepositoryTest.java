package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.springjpamission.order.domain.Car;
import com.example.springjpamission.order.domain.Order;
import com.example.springjpamission.order.domain.OrderItem;
import com.example.springjpamission.order.domain.OrderRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    private Order testOrder;
    private String uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID().toString();

        Order order = new Order();
        order.setMemo("---");
        order.setId(uuid);

        testOrder = orderRepository.save(order);
    }

    @Test
    void customerMappingTest() {
        Customer customer = new Customer();
        customer.setFirstName("별");
        customer.setLastName("김");

        testOrder.setCustomer(customer);

        Order updatedOrder = orderRepository.save(testOrder);

        assertThat(updatedOrder.getCustomer().getFirstName()).isEqualTo("별");
    }

    @Test
    void orderItemMappingTest() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(10);

        testOrder.addOrderItem(orderItem);

        Order updatedOrder = orderRepository.save(testOrder);

        assertThat(updatedOrder.getOrderItems().get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    void itemMappingTest() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(10);

        Car item = new Car();
        item.setPrice(1000);
        item.setPower(200);
        item.setStockQuantity(100);

        orderItem.addItem(item);

        testOrder.addOrderItem(orderItem);

        Order updatedOrder = orderRepository.save(testOrder);

        assertThat(updatedOrder.getOrderItems().get(0).getItems().get(0).getPrice()).isEqualTo(
                1000);
    }

}
