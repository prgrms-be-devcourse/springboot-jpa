package com.programmers.springbootjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springbootjpa.domain.customer.Address;
import com.programmers.springbootjpa.domain.customer.Customer;
import com.programmers.springbootjpa.domain.item.Item;
import com.programmers.springbootjpa.domain.order.Order;
import com.programmers.springbootjpa.domain.order.OrderItem;
import com.programmers.springbootjpa.domain.order.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AssociationTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("고객을 통해 주문 리스트를 조회할 수 있다.")
    void getOrdersByCustomer() {
        Customer customer = Customer.builder()
                .name("hyunHo")
                .nickName("changHyun")
                .age(28)
                .address(new Address("스터릿 어드레스", "디테일 어드레스", 10000))
                .build();

        Order order1 = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("메모1")
                .build();

        order1.setCustomer(customer);

        Order order2 = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("메모2")
                .build();

        order2.setCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);
        Order savedOrder1 = orderRepository.save(order1);
        Order savedOrder2 = orderRepository.save(order2);

        assertThat(savedOrder1.getMemo()).isEqualTo(savedCustomer.getOrders().get(0).getMemo());
        assertThat(savedOrder2.getMemo()).isEqualTo(savedCustomer.getOrders().get(1).getMemo());
    }

    @Test
    @DisplayName("주문에서 고객을 조회할 수 있다.")
    void getCustomerByOrder() {
        Customer customer = Customer.builder()
                .name("hyunHo")
                .nickName("changHyun")
                .age(28)
                .address(new Address("스터릿 어드레스", "디테일 어드레스", 10000))
                .build();

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("메모1")
                .build();

        order.setCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);
        Order savedOrder = orderRepository.save(order);

        assertThat(savedCustomer.getNickName()).isEqualTo(savedOrder.getCustomer().getNickName());
    }

    @Test
    @DisplayName("주문 아이템에서 주문을 조회할 수 있다.")
    void getOrderByOrderItem() {
        Customer customer = Customer.builder()
                .name("hyunHo")
                .nickName("changHyun")
                .age(28)
                .address(new Address("스터릿 어드레스", "디테일 어드레스", 10000))
                .build();

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("메모1")
                .build();

        order.setCustomer(customer);

        Item item = Item.builder()
                .name("매운갈비찜")
                .price(3000)
                .stockQuantity(10)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .quantity(3)
                .price(9000)
                .item(item)
                .order(order)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        Order savedOrder = orderRepository.save(order);
        Item savedItem = itemRepository.save(item);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        assertThat(savedOrder.getUuid()).isEqualTo(savedOrderItem.getOrder().getUuid());
    }


    @Test
    @DisplayName("주문 아이템에서 아이템을 조회할 수 있다.")
    void getItemByOrderItem() {
        Customer customer = Customer.builder()
                .name("hyunHo")
                .nickName("changHyun")
                .age(28)
                .address(new Address("스터릿 어드레스", "디테일 어드레스", 10000))
                .build();

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("메모1")
                .build();

        order.setCustomer(customer);

        Item item = Item.builder()
                .name("매운갈비찜")
                .price(3000)
                .stockQuantity(10)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .quantity(3)
                .price(9000)
                .item(item)
                .order(order)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        Order savedOrder = orderRepository.save(order);
        Item savedItem = itemRepository.save(item);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        assertThat(savedItem.getName()).isEqualTo(savedOrderItem.getItem().getName());
    }
}
