package com.jpaweekily.domain.order.application;

import com.jpaweekily.domain.order.dto.OrderCreateRequest;
import com.jpaweekily.domain.order.dto.OrderProductCreate;
import com.jpaweekily.domain.product.application.ProductService;
import com.jpaweekily.domain.product.dto.ProductCreateRequest;
import com.jpaweekily.domain.user.application.UserService;
import com.jpaweekily.domain.user.dto.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceImplTest {

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
    void order_create_test() {
        Long userId = userService.createUser(new UserCreateRequest("test", "123", "tester"));
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest("tester", "수원시", orderProducts);

        Long orderId = orderService.createOrder(userId, orderCreateRequest);

        assertThat(orderId).isNotNull();
    }

}
