package com.programmers.springbootjpa.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        Member member = new Member("hyemin", "nick", 26, "서울특별시 성북구");

        Item car = new Car(10, 10, 12);
        itemRepository.save(car);
        OrderItem carOrderItem = new OrderItem(2, car);

        Item food = new Food(20, 20, "Jung");
        itemRepository.save(food);
        OrderItem foodOrderItem = new OrderItem(3, food);

        List<OrderItem> orderItems = Arrays.asList(carOrderItem, foodOrderItem);

        order = new Order(OrderStatus.OPENED, "orderMemo", member, orderItems);
    }

    @DisplayName("주문을 저장한다")
    @Test
    void testSave() {
        //given
        //when
        Order savedOrder = orderRepository.save(order);

        //then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());
        assertThat(savedOrder.getMemo()).isEqualTo(order.getMemo());
        assertThat(savedOrder.getMember()).isEqualTo(order.getMember());
        assertThat(savedOrder.getOrderItems()).isEqualTo(order.getOrderItems());
    }

    @DisplayName("주문을 수정한다")
    @Test
    void testUpdate() {
        //given
        Order savedOrder = orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        Item car = new Car(22, 22, 12);
        itemRepository.save(car);
        OrderItem carOrderItem = new OrderItem(4, car);

        List<OrderItem> orderItems = Arrays.asList(carOrderItem);

        //when
        savedOrder.updateOrderStatus(OrderStatus.CANCELLED);
        savedOrder.updateMemo("updateMemo");
        savedOrder.updateMember(member);
        savedOrder.updateOrderItems(orderItems);

        //then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(savedOrder.getMemo()).isEqualTo("updateMemo");
        assertThat(savedOrder.getMember()).isEqualTo(member);
        assertThat(savedOrder.getOrderItems()).containsAll(orderItems);
    }

    @DisplayName("주문을 id로 조회한다")
    @Test
    void testFindById() {
        //given
        Order savedOrder = orderRepository.save(order);

        //when
        Order result = orderRepository.findById(savedOrder.getId()).get();

        //then
        assertThat(result.getOrderStatus()).isEqualTo(savedOrder.getOrderStatus());
        assertThat(result.getMember()).isEqualTo(savedOrder.getMember());
        assertThat(result.getOrderItems()).containsAll(savedOrder.getOrderItems());
    }

    @DisplayName("저장된 주문을 모두 조회한다")
    @Test
    void testFindAll() {
        //given
        orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        Item car = new Car(22, 22, 12);
        itemRepository.save(car);
        OrderItem carOrderItem = new OrderItem(4, car);

        Order order2 = new Order(OrderStatus.OPENED, "orderMemo2", member, List.of(carOrderItem));
        orderRepository.save(order2);

        //when
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).hasSize(2);
    }

    @DisplayName("주문을 삭제한다")
    @Test
    void testDelete() {
        //given
        Order savedOrder = orderRepository.save(order);

        //when
        orderRepository.delete(savedOrder);
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).isEmpty();
    }

    @DisplayName("저장된 주문을 모두 삭제한다")
    @Test
    void testDeleteAll() {
        //given
        orderRepository.save(order);

        Member member = new Member("min", "nicky", 20, "경기도");

        Item car = new Car(22, 22, 12);
        itemRepository.save(car);
        OrderItem carOrderItem = new OrderItem(4, car);

        Order order2 = new Order(OrderStatus.OPENED, "orderMemo2", member, List.of(carOrderItem));
        orderRepository.save(order2);

        //when
        orderRepository.deleteAll();
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).isEmpty();
    }
}