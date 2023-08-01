package org.wonu606.jpashop.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.wonu606.jpashop.item.domain.Book;
import org.wonu606.jpashop.item.domain.ItemRepository;
import org.wonu606.jpashop.item.domain.PerformanceTicket;

@DataJpaTest
class OrderRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryTest.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Order savedOrder;

    @BeforeEach
    void setUp() {
        // Given
        Book book = new Book(19800, 90, "James", "978-0-7334-2609-4");
        Book savedBook = itemRepository.save(book);

        PerformanceTicket performanceTicket = new PerformanceTicket(89000, 432, "Opera House");
        PerformanceTicket savedTicket = itemRepository.save(performanceTicket);

        OrderLineItem orderLineItemForBook =
                new OrderLineItem(
                        19800,
                        1,
                        savedBook);

        OrderLineItem orderLineItemForPerformanceTicket =
                new OrderLineItem(
                        898980,
                        324,
                        savedTicket);

        Order order = new Order(LocalDateTime.now(), new ArrayList<>());
        order.addOrderLineItem(orderLineItemForBook);
        order.addOrderLineItem(orderLineItemForPerformanceTicket);

        savedOrder = orderRepository.save(order);
    }

    @Test
    void findAll() {
        List<Order> orderAllList = orderRepository.findAll();

        log.info("orderAllList = {}", orderAllList);
    }

    @Test
    void findOne() {
        Optional<Order> retrievedOrder = orderRepository.findByIdWithOrderLineItemsAndItems(
                savedOrder.getId());
        log.info("{}", retrievedOrder.get());
    }
}
