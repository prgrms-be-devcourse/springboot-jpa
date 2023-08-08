package com.jhs.springbootjpa.persistence;

import com.jhs.springbootjpa.model.Customer;
import com.jhs.springbootjpa.model.Item;
import com.jhs.springbootjpa.model.Order;
import com.jhs.springbootjpa.repository.CustomerRepository;
import com.jhs.springbootjpa.repository.ItemRepository;
import com.jhs.springbootjpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderPersistenceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    Customer customer;
    List<Item> items = new ArrayList<>();

    @BeforeEach
    void init() {
         customer = customerRepository.save(new Customer(null, "홍석", "주"));

        for (int i = 1; i <= 10; i++) {
            items.add(itemRepository.save(new Item(null, "item" + i, 1000, 10)));
        }
    }

    @Test
    @DisplayName("Order와 OrderItem 저장")
    void saveOrderAndOrderItem() {
        Order order = new Order(null, "부재 시 전화 주세요", customer);
        order.addItem(items.get(0), 5);

        Order savedOrder = orderRepository.save(order);

        assertThat(savedOrder.getOrderItems()).hasSize(1);
    }

    @Test
    @DisplayName("Order와 OrderItem 동시 조회")
    void findOrderItemByOrder() {
        Order order = new Order(null, "부재 시 전화 주세요", customer);
        order.addItem(items.get(1), 5);
        order.addItem(items.get(2), 5);
        order.addItem(items.get(3), 5);
        order.addItem(items.get(4), 5);

        Order savedOrder = orderRepository.save(order);

        Order findOrder = orderRepository.findById(savedOrder.getId()).get();

        assertThat(findOrder.getOrderItems()).hasSize(4);
        assertThat(findOrder.getOrderItems().get(0))
                .hasFieldOrPropertyWithValue("item", items.get(1));
    }

}
