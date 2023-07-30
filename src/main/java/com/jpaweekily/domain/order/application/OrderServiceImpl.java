package com.jpaweekily.domain.order.application;

import com.jpaweekily.domain.order.Order;
import com.jpaweekily.domain.order.OrderProduct;
import com.jpaweekily.domain.order.OrderStatus;
import com.jpaweekily.domain.order.dto.OrderCreateRequest;
import com.jpaweekily.domain.order.infrastructrue.OrderProductRepository;
import com.jpaweekily.domain.order.infrastructrue.OrderRepository;
import com.jpaweekily.domain.product.Product;
import com.jpaweekily.domain.product.infrastructrue.ProductRepository;
import com.jpaweekily.domain.user.User;
import com.jpaweekily.domain.user.infrastructrue.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public Long createOrder(OrderCreateRequest request) {
        User user = userRepository.findByNickName(request.nickName()).orElseThrow(IllegalArgumentException::new);

        Order order = Order.builder()
                .address(request.address())
                .orderStatus(OrderStatus.READY_FOR_DELIVERY)
                .createAt(LocalDateTime.now())
                .user(user)
                .build();
        orderRepository.save(order);

        request.orderProductCreateList().forEach(item -> {
            Product product = productRepository.findById(item.productId()).orElseThrow(IllegalArgumentException::new);
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.quantity())
                    .build();
            orderProductRepository.save(orderProduct);
        });
        return order.getId();
    }
}
