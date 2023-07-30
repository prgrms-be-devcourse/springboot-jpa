package com.jpaweekily.domain.order.application;

import com.jpaweekily.domain.order.dto.OrderCreateRequest;
import com.jpaweekily.domain.order.dto.OrderProductCreate;
import com.jpaweekily.domain.product.application.ProductService;
import com.jpaweekily.domain.product.dto.ProductCreateRequest;
import com.jpaweekily.domain.user.application.UserService;
import com.jpaweekily.domain.user.dto.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Test
    void order_create_test() {
        userService.createUser(new UserCreateRequest("test", "123", "tester"));
        Long productId = productService.createProduct(new ProductCreateRequest("주먹밥", 100));
        OrderProductCreate orderProductCreate = new OrderProductCreate(productId, 3);
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest("tester", "수원시", List.of(orderProductCreate));

        Long orderId = orderService.createOrder(orderCreateRequest);

        assertThat(orderId).isNotNull();
    }

}
