package com.devcourse.mission.domain.order.service;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.customer.repository.CustomerRepository;
import com.devcourse.mission.domain.item.repository.ItemRepository;
import com.devcourse.mission.domain.order.entity.Order;
import com.devcourse.mission.domain.order.repository.OrderRepository;
import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public long order(long customerId, List<OrderItem> orderItems) {
        Customer findCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원 번호 입니다."));

        Order order = Order.createOrder(findCustomer);
        orderItems.forEach(order::addOrderItem);

        orderItems.forEach(orderItem -> itemRepository.findById(orderItem.getItem().getId())
                .ifPresent((item) -> item.decreaseQuantity(orderItem.getQuantity())));

        findCustomer.order(order);

        return orderRepository.save(order).getId();
    }

    public Order getSimpleOrderById(long orderId) {
        return orderRepository.joinOrderItemByOrderId(orderId);
    }

    public Order getDetailOrderById(long orderId) {
        return orderRepository.joinCustomerOrderItemByOrderId(orderId);
    }

    public List<Long> getOrderItemIdsById(long orderId) {
        return orderRepository.joinOrderItemByOrderId(orderId)
                .getOrderItems()
                .stream()
                .map(OrderItem::getId)
                .toList();
    }

    @Transactional
    public void deleteById(long orderId) {
        Order order = orderRepository.joinOrderItemByOrderId(orderId);
        order.getOrderItems().forEach(OrderItem::unrollItem);
        orderRepository.deleteById(orderId);
    }
}
