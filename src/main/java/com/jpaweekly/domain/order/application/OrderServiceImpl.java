package com.jpaweekly.domain.order.application;

import com.jpaweekly.domain.order.Order;
import com.jpaweekly.domain.order.OrderProduct;
import com.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.jpaweekly.domain.order.dto.OrderProductResponse;
import com.jpaweekly.domain.order.dto.OrderResponse;
import com.jpaweekly.domain.order.infrastructrue.OrderProductRepository;
import com.jpaweekly.domain.order.infrastructrue.OrderRepository;
import com.jpaweekly.domain.product.Product;
import com.jpaweekly.domain.product.infrastructrue.ProductRepository;
import com.jpaweekly.domain.user.User;
import com.jpaweekly.domain.user.infrastructrue.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Order order = request.toEntity(user);
        Order savedOrder = orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        request.orderProductCreateList().forEach(item -> {
            Product product = productRepository.findById(item.productId()).orElseThrow(EntityNotFoundException::new);
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(item.quantity())
                    .build();
            orderProducts.add(orderProduct);
        });
        orderProductRepository.saveAll(orderProducts);
        return savedOrder.getId();
    }

    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return OrderResponse.from(order);
    }

    public Page<OrderResponse> findOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderResponse::from);
    }

    public Page<OrderProductResponse> findOrderProducts(Pageable pageable) {
        return orderProductRepository.findAll(pageable)
                .map(OrderProductResponse::from);
    }

    public Page<OrderProductResponse> findOrderProductsByOderId(Long orderId, Pageable pageable) {
        return orderProductRepository.findByOrderId(orderId, pageable)
                .map(OrderProductResponse::from);
    }

    @Transactional
    public void deliverOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.changeOrderStatusToDelivering();
    }

    @Transactional
    public void completeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.changeOrderStatusToComplete();
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
