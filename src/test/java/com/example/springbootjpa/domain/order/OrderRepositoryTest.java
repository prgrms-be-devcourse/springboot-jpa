package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.domain.customer.Customer;
import com.example.springbootjpa.domain.customer.CustomerRepository;
import com.example.springbootjpa.domain.item.Item;
import com.example.springbootjpa.domain.item.Keyboard;
import com.example.springbootjpa.domain.item.Mouse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Order 저장시, OrderItem,Item 함께 저장")
    void createOrderTest() throws Exception {

        //given
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .username("hong")
                .address("부산시")
                .build());

        int initialStockQuantity = 10;
        Item item1 = new Mouse(1000, 10, "red");
        Item item2 = new Keyboard(1000, 10, "blue");
        int requestQuantity = 1;

        OrderItem orderItem1 = OrderItem.create(item1, item1.getPrice(), requestQuantity);
        OrderItem orderItem2 = OrderItem.create(item2, item2.getPrice(), requestQuantity);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        Order order = Order.createOrder(savedCustomer, orderItems);

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        assertThat(savedOrder.getCustomer().getUsername()).isEqualTo(savedCustomer.getUsername());
        assertThat(savedOrder.getOrderItems().get(0).getItem().getPrice()).isEqualTo(item1.getPrice());
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(item1.getStockQuantity()).isEqualTo(initialStockQuantity - requestQuantity);
    }
}