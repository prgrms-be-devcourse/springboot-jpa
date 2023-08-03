package com.example.jpaweekly.domain.order.service;

import com.example.jpaweekly.domain.order.Order;
import com.example.jpaweekly.domain.order.OrderProduct;
import com.example.jpaweekly.domain.order.OrderStatus;
import com.example.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.example.jpaweekly.domain.order.dto.OrderResponse;
import com.example.jpaweekly.domain.order.repository.OrderProductRepository;
import com.example.jpaweekly.domain.order.repository.OrderRepository;
import com.example.jpaweekly.domain.product.Product;
import com.example.jpaweekly.domain.product.repository.ProductRepository;
import com.example.jpaweekly.domain.user.User;
import com.example.jpaweekly.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Transactional
    public Long createOrder(OrderCreateRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Order order = Order.builder()
                .address(request.address())
                .orderStatus(OrderStatus.READY_FOR_DELIVERY)
                .user(user)
                .build();

        orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        request.orderProducts().forEach(v -> {
            Product product = productRepository.findById(v.productId()).orElseThrow(IllegalArgumentException::new);
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(v.quantity())
                    .build();
            orderProducts.add(orderProduct);
        });
        orderProductRepository.saveAll(orderProducts);

        return order.getId();
    }


}
