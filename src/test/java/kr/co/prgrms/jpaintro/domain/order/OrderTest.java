package kr.co.prgrms.jpaintro.domain.order;

import kr.co.prgrms.jpaintro.domain.customer.Customer;
import kr.co.prgrms.jpaintro.domain.customer.CustomerRepository;
import kr.co.prgrms.jpaintro.domain.item.Food;
import kr.co.prgrms.jpaintro.domain.item.Item;
import kr.co.prgrms.jpaintro.domain.item.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("예성", "고");
        customerRepository.save(customer);

        order = new Order();
        order.updateOrderStatus(OrderStatus.OPENED);
        order.updateMemo("맛있는거 시켜먹자~~");
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem(20_000, 1);
        orderItemRepository.save(orderItem);

        Item item = new Food(20_000, 50, "황금올리브치킨");
        itemRepository.save(item);

        order.addOrderItem(orderItem);
    }

    @Test
    void 주문조회_테스트() {
        // given
        Order savedOrder = orderRepository.save(order);
        Long id = savedOrder.getId();
        // when
        Optional<Order> response = orderRepository.findById(id);
        Order foundOrder = response.orElseThrow();

        // then
        assertThat(foundOrder.getId())
                .isEqualTo(savedOrder.getId());
    }

    @Test
    void 주문상품추가_테스트() {
        // given
        Order savedOrder = orderRepository.save(order);
        Item item = new Food(15_000, 50, "배달삼겹살");
        itemRepository.save(item);

        OrderItem orderItem = new OrderItem(15_000, 2);


        // when

        // then
    }
}
