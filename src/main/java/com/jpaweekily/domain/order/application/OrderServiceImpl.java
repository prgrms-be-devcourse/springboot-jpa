package com.jpaweekily.domain.order.application;

import com.jpaweekily.domain.order.Order;
import com.jpaweekily.domain.order.OrderProduct;
import com.jpaweekily.domain.order.OrderStatus;
import com.jpaweekily.domain.order.dto.OrderCreateRequest;
import com.jpaweekily.domain.order.dto.OrderResponse;
import com.jpaweekily.domain.order.infrastructrue.OrderProductRepository;
import com.jpaweekily.domain.order.infrastructrue.OrderRepository;
import com.jpaweekily.domain.product.Product;
import com.jpaweekily.domain.product.infrastructrue.ProductRepository;
import com.jpaweekily.domain.user.User;
import com.jpaweekily.domain.user.infrastructrue.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long createOrder(Long id, OrderCreateRequest request) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Order order = Order.builder()
                .address(request.address())
                .orderStatus(OrderStatus.READY_FOR_DELIVERY)
                .createAt(LocalDateTime.now())
                .user(user)
                .build();
        orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        request.orderProductCreateList().forEach(item -> {
            Product product = productRepository.findById(item.productId()).orElseThrow(IllegalArgumentException::new);
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.quantity())
                    .build();
            orderProducts.add(orderProduct);
        });
        orderProductRepository.saveAll(orderProducts);
        return order.getId();
    }

    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return OrderResponse.from(order);
    }
}
