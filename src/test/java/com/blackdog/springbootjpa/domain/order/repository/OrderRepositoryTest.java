package com.blackdog.springbootjpa.domain.order.repository;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.domain.customer.repository.CustomerRepository;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import com.blackdog.springbootjpa.domain.customer.vo.Name;
import com.blackdog.springbootjpa.domain.item.model.Item;
import com.blackdog.springbootjpa.domain.item.repository.ItemRepository;
import com.blackdog.springbootjpa.domain.item.vo.OriginNation;
import com.blackdog.springbootjpa.domain.item.vo.Price;
import com.blackdog.springbootjpa.domain.order.model.Order;
import com.blackdog.springbootjpa.domain.order.model.OrderItem;
import com.blackdog.springbootjpa.domain.order.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;

    Customer customer;
    Item item;
    Order order;
    OrderItem orderItem;

    @BeforeEach
    void setup() {
        setUpCustomer();
        setUpItem();
        setUpSavedOrder();
    }

    @Test
    @DisplayName("주문을 추가한다.")
    void save_Order() {
        //when
        Order savedOrder = repository.save(order);

        //then
        Order result = repository.findById(order.getId()).get();
        assertThat(result.getId()).isEqualTo(savedOrder.getId());
        assertThat(result.getOrderItems()).hasSameSizeAs(savedOrder.getOrderItems());
    }

    @Test
    @DisplayName("주문을 삭제할 수 있다.")
    void delete_Order() {
        //given
        Order savedOrder = repository.save(order);
        assertThat(repository.findAll().size()).isEqualTo(1);

        //when
        repository.deleteById(savedOrder.getId());

        //then
        assertThat(repository.findAll().size()).isEqualTo(0);
        Optional<Order> result = repository.findById(savedOrder.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("주문으로 고객을 조회할 수 있다.")
    void findCustomer_Order_Request() {
        // given
        Order savedOrder = repository.save(order);

        // when
        Order result = repository.findById(savedOrder.getId()).get();

        // then
        assertThat(result.getCustomer()).isNotNull();
        assertThat(result.getCustomer().getName()).isEqualTo(customer.getName());
    }

    @Test
    @DisplayName("주문번호를 통해 주문목록과 주문한 상품의 정보를 조회할 수 있다.")
    void findOrderItemAndItem_OrderId_Success() {
        //given
        Order savedOrder = repository.save(order);

        //when
        Order findOrder = repository.findOrderWithDetails(savedOrder.getId()).get();

        //then
        assertThat(findOrder.getOrderItems()).hasSize(1);
        assertThat(findOrder.getOrderItems().get(0).getItem().getNation()).isEqualTo(item.getNation());
        assertThat(findOrder.getOrderItems()).usingRecursiveComparison().isEqualTo(List.of(orderItem));
    }

    private void setUpCustomer() {
        customer = Customer.builder()
                .name(new Name("둘리"))
                .age(new Age(12000))
                .email(new Email("dooli@naver.com"))
                .build();
        customerRepository.save(customer);
    }

    private void setUpSavedOrder() {
        order = Order.builder()
                .orderStatus(OrderStatus.ACCEPTED)
                .customer(customer)
                .build();
        orderItem = OrderItem.builder()
                .item(item)
                .count(4)
                .order(order)
                .build();
        order.addOrderItem(orderItem);
    }

    private void setUpItem() {
        item = Item.builder()
                .price(new Price(1000))
                .nation(new OriginNation("Kr"))
                .build();
        itemRepository.save(item);
    }
}
