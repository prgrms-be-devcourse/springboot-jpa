package kr.co.prgrms.jpaintro.domain.order;

import kr.co.prgrms.jpaintro.domain.customer.Customer;
import kr.co.prgrms.jpaintro.domain.customer.CustomerRepository;
import kr.co.prgrms.jpaintro.domain.item.Food;
import kr.co.prgrms.jpaintro.domain.item.Item;
import kr.co.prgrms.jpaintro.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("예성", "고");
        customerRepository.save(customer);
        order = new Order(customer);

        Item item = new Food("BBQ황금올리브", 20_000, 50, "치킨");
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(order, item);
        orderItem.changeOrderItemQuantity(2);
        order.addOrderItem(orderItem);
    }

    @Test
    void 주문조회_테스트() {
        // given
        Order savedOrder = orderRepository.save(order);
        Long id = savedOrder.getId();

        // when
        Optional<Order> response = orderRepository.findById(id);
        Order result = response.orElseThrow();

        // then
        assertThat(result.getId())
                .isEqualTo(id);
    }

    @Test
    void 주문상품추가_테스트() {
        // given
        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getId();
        Item newItem = new Food("허니콤보", 23_000, 50, "치킨");
        itemRepository.save(newItem);
        OrderItem orderItem = new OrderItem(order, newItem);

        // when
        orderItem.changeOrderItemQuantity(3);
        order.addOrderItem(orderItem);
        Optional<Order> response = orderRepository.findById(savedOrderId);
        Order result = response.orElseThrow();

        // then
        assertThat(result.getOrderItems())
                .hasSize(2);
    }

    @Test
    void 주문삭제_테스트() {
        // given
        Order savedOrder = orderRepository.save(order);
        Long savedOrderId = savedOrder.getId();

        // when
        orderRepository.deleteById(savedOrderId);

        // then
        Optional<Order> response = orderRepository.findById(savedOrderId);
        assertThat(response)
                .isEmpty();
    }
}
