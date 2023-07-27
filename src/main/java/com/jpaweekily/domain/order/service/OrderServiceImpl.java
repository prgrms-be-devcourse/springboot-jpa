package com.jpaweekily.domain.order.service;

import com.jpaweekily.domain.order.Order;
import com.jpaweekily.domain.order.OrderStatus;
import com.jpaweekily.domain.order.dto.OrderCreateRequest;
import com.jpaweekily.domain.order.repository.OrderRepository;
import com.jpaweekily.domain.user.User;
import com.jpaweekily.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void createOrder(OrderCreateRequest request) {
        User user = userRepository.findByNickName(request.nickName()).orElseThrow(IllegalArgumentException::new);

        Order order = Order.builder()
                .address(request.address())
                .orderStatus(OrderStatus.READY_FOR_DELIVERY)
                .createAt(LocalDateTime.now())
                .user(user)
                .build();

        orderRepository.save(order);
    }
}
