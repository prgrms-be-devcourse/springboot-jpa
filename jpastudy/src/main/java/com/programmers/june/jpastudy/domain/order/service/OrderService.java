package com.programmers.june.jpastudy.domain.order.service;

import com.programmers.june.jpastudy.domain.customer.entity.Customer;
import com.programmers.june.jpastudy.domain.customer.repository.CustomerRepository;
import com.programmers.june.jpastudy.domain.item.entity.Item;
import com.programmers.june.jpastudy.domain.item.repository.ItemRepository;
import com.programmers.june.jpastudy.domain.order.entity.Order;
import com.programmers.june.jpastudy.domain.order.repository.OrderRepository;
import com.programmers.june.jpastudy.domain.order_item.entity.OrderItem;
import com.programmers.june.jpastudy.domain.order_item.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long order(Long customerId, Long itemId, int orderQuantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Not Found!"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item Not Found!"));

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderQuantity);

        // 주문 생성
        Order order = Order.createOrder(customer, orderItem);

        orderItemRepository.save(orderItem);

        return orderRepository.save(order).getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found!"));

        order.cancel(order);
    }
}
