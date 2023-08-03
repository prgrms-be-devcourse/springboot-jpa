package com.jpaweekly.domain.order.application;

import com.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.jpaweekly.domain.order.dto.OrderProductCreate;
import com.jpaweekly.domain.product.application.ProductService;
import com.jpaweekly.domain.product.dto.ProductCreateRequest;
import com.jpaweekly.domain.user.application.UserService;
import com.jpaweekly.domain.user.dto.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderServiceImplTest {

    @TestConfiguration
    @ComponentScan(basePackages = {"com.jpaweekly.domain", "com.jpaweekly.config"})
    static class Config{}

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    List<OrderProductCreate> orderProducts;

    @BeforeEach
    void setUp() {
        Long productId = productService.createProduct(new ProductCreateRequest("주먹밥", 100));
        Long productId2 = productService.createProduct(new ProductCreateRequest("고봉밥", 100));
        Long productId3 = productService.createProduct(new ProductCreateRequest("김밥", 100));
        OrderProductCreate orderProductCreate = new OrderProductCreate(productId, 3);
        OrderProductCreate orderProductCreate2 = new OrderProductCreate(productId2, 3);
        OrderProductCreate orderProductCreate3 = new OrderProductCreate(productId3, 3);
        orderProducts = List.of(orderProductCreate, orderProductCreate2, orderProductCreate3);
    }

    @Test
    void orderCreateTest() {
        Long userId = userService.createUser(new UserCreateRequest("test", "123", "tester"));
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest("tester", "수원시", orderProducts);

        Long orderId = orderService.createOrder(userId, orderCreateRequest);

        assertThat(orderId).isNotNull();
    }

}
