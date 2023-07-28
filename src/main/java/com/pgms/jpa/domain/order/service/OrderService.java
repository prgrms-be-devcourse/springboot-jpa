package com.pgms.jpa.domain.order.service;

import com.pgms.jpa.domain.item.Item;
import com.pgms.jpa.domain.item.ItemRepository;
import com.pgms.jpa.domain.order.Order;
import com.pgms.jpa.domain.order.OrderItem;
import com.pgms.jpa.domain.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public OrderService(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long createOrder(Long itemId, int orderQuantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("아이템을 찾을 수 없습니다."));

        OrderItem orderItem = OrderItem.createOrderItem(item, orderQuantity);
        Order order = Order.createOrder(List.of(orderItem));

        Long savedId = orderRepository.save(order);
        return savedId;
    }

    public Order findOne(Long orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문을 찾을 수 없습니다."));

        return findOrder;
    }
}
