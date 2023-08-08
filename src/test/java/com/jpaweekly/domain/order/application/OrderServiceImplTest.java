package com.jpaweekly.domain.order.application;

import com.jpaweekly.domain.order.Order;
import com.jpaweekly.domain.order.OrderProduct;
import com.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.jpaweekly.domain.order.dto.OrderProductCreate;
import com.jpaweekly.domain.order.infrastructrue.OrderProductRepository;
import com.jpaweekly.domain.order.infrastructrue.OrderRepository;
import com.jpaweekly.domain.product.Product;
import com.jpaweekly.domain.product.infrastructrue.ProductRepository;
import com.jpaweekly.domain.user.User;
import com.jpaweekly.domain.user.infrastructrue.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Assign.valueOf;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    Long initialId = 1L;

    @Test
    void orderCreateTest() {
        //given
        int productSize = 100;
        int orderProductCreateSize = 10;

        List<Product> productList = getProductList(productSize);
        List<OrderProductCreate> orderProductCreateList = getOrderProductList(orderProductCreateSize, productSize);
        OrderCreateRequest request = getOrderCreateRequest(orderProductCreateList);

        User user = Instancio.create(User.class);
        Long userId = user.getId();

        Order order = request.toEntity(user);
        ReflectionTestUtils.setField(order, "id", initialId);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        for (int i = 0; i < orderProductCreateList.size(); i++) {
            long productId = orderProductCreateList.get(i).productId();
            int productIdx = (int) productId - 1;
            given(productRepository.findById(productId)).willReturn(Optional.ofNullable(productList.get(productIdx)));
        }
        doNothing().when(orderProductRepository).saveAll(anyList());

        //when
        Long orderId = orderService.createOrder(userId, request);

        //then
        assertThat(order.getId()).isEqualTo(orderId);
    }


    private List<Product> getProductList(int size) {
        return Instancio.ofList(Product.class)
                .size(size)
                .assign(valueOf(Product::getId).generate(gen -> gen.longSeq().start(initialId)))
                .create();
    }

    private List<OrderProductCreate> getOrderProductList(int size, long productSize) {
        return Instancio.ofList(OrderProductCreate.class)
                .size(size)
                .supply(field(OrderProductCreate::productId), random -> random.longRange(initialId, productSize))
                .create();
    }

    private OrderCreateRequest getOrderCreateRequest(List<OrderProductCreate> orderProductCreateList) {
        return Instancio.of(OrderCreateRequest.class)
                .set(field(OrderCreateRequest::orderProductCreateList), orderProductCreateList)
                .create();
    }

    private List<OrderProduct> getOrderProductList(
            List<OrderProductCreate> orderProductCreateList,
            List<Product> productList,
            Order order
    ) {
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < orderProductCreateList.size(); i++) {
            int productIdx = (int) (orderProductCreateList.get(i).productId() - 1);
            OrderProduct orderProduct = Instancio.of(OrderProduct.class)
                    .set(field(OrderProduct::getOrder), order)
                    .set(field(OrderProduct::getProduct), productList.get(productIdx))
                    .set(field(OrderProduct::getQuantity), orderProductCreateList.get(i).quantity())
                    .create();
            orderProductList.add(orderProduct);
        }
        return orderProductList;
    }

}
