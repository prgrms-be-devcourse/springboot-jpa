package com.example.jpaweekly.domain.order.service;

import com.example.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.example.jpaweekly.domain.order.dto.OrderProductCreateRequest;
import com.example.jpaweekly.domain.order.dto.OrderResponse;
import com.example.jpaweekly.domain.product.dto.ProductCreateRequest;
import com.example.jpaweekly.domain.product.service.ProductService;
import com.example.jpaweekly.domain.user.dto.UserCreateRequest;
import com.example.jpaweekly.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    List<OrderProductCreateRequest> orderProducts = new ArrayList<>();

    @BeforeEach
    void setUp() {

        ProductCreateRequest productCreateRequest1 = new ProductCreateRequest("icecream", 3000);
        ProductCreateRequest productCreateRequest2 = new ProductCreateRequest("cake", 6500);
        Long productId1 = productService.createProduct(productCreateRequest1);
        Long productId2 = productService.createProduct(productCreateRequest2);

        OrderProductCreateRequest orderProductCreateRequest1 = new OrderProductCreateRequest(productId1, 6);
        OrderProductCreateRequest orderProductCreateRequest2 = new OrderProductCreateRequest(productId2, 3);
        orderProducts.add(orderProductCreateRequest1);
        orderProducts.add(orderProductCreateRequest2);
    }

    @Test
    void createTest() {
        //Given
        UserCreateRequest userCreateRequest = new UserCreateRequest("login", "password", "닉네임");
        Long userId = userService.createUser(userCreateRequest);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest("서울시 동작구", orderProducts);

        // When
        Long orderId = orderService.createOrder(orderCreateRequest, userId);

        // Then
        assertThat(orderId).isNotNull();
    }

    @Test
    void findByIdTest() {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest("login", "password", "닉네임");
        Long userId = userService.createUser(userCreateRequest);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest("서울시 동작구", orderProducts);
        Long orderId = orderService.createOrder(orderCreateRequest, userId);

        // When
        OrderResponse orderResponse = orderService.findOrderById(orderId);

        // Then
        assertThat(orderResponse.id()).isEqualTo(orderId);

    }

}
