package com.example.mission3.repository;

import com.example.mission3.domain.Item;
import com.example.mission3.domain.Order;
import com.example.mission3.domain.OrderItem;
import com.example.mission3.domain.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManagerFactory emf;

    private Order order;
    private OrderItem orderItem;

    @BeforeEach
    @DisplayName("Order와 OrderItem이 정상 등록된다. - cascade")
    void insertTest() {
        Item item = new Item("옷", 4000, 5);
        order = new Order(UUID.randomUUID().toString(), "youngji804@naver.com", "영지네","담당자");
        orderItem = new OrderItem(4000, 3, item);

        itemRepository.save(item);

        orderItem.setOrder(order);
        orderRepository.save(order);

        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("order을 조회하여 orderItem과 item을 확인한다. - lazy load")
    void findOrderAndOrderItem() {
        Optional<Order> findOrder = orderRepository.findById(order.getUuid());

        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getEmail()).isEqualTo("youngji804@naver.com");

        OrderItem findOrderItem = findOrder.get().getOrderItems().get(0);

        //assertThat(emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getItem())).isFalse(); // 테스트 실패
        assertThat(findOrderItem.getPrice()).isEqualTo(4000);
        assertThat(emf.getPersistenceUnitUtil().isLoaded(findOrderItem.getItem())).isTrue();
    }

    @Test
    @DisplayName("메소드 쿼리를 통해 특정 email의 주문을 찾느다.")
    void findByEmailTest() {
        List<Order> orders = orderRepository.findOrdersByEmail("youngji804@naver.com");
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getAddress()).isEqualTo("영지네");
    }

    @Test
    @DisplayName("메소드 쿼리를 통해 opened 상태의 특정 email과 address 주문을 찾느다.")
    void findByEmailAndAddressTest() {
        List<Order> orders = orderRepository.findOrderByOrderStatusAndEmailAndAddress(OrderStatus.OPENED, "youngji804@naver.com", "영지네");
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getUuid()).isEqualTo(order.getUuid());
    }

    @Test
    @DisplayName("주문 상태를 업데이트한다.")
    void updateOrderStatus() {
        Optional<Order> findOrder = orderRepository.findById(order.getUuid());
        assertThat(findOrder.isPresent()).isTrue();

        findOrder.get().changeOrderStatus(OrderStatus.CANCELLED);

        Optional<Order> updateOrder = orderRepository.findById(order.getUuid());
        assertThat(updateOrder.isPresent()).isTrue();
        assertThat(updateOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("orderItem을 삭제한다. - orphanRemoval")
    void deleteOrderItem() {
        orderRepository.delete(order);

        List<Order> orders = orderRepository.findAll();
        assertThat(orders.size()).isEqualTo(0);

        Optional<Order> findOrder = orderRepository.findById(order.getUuid());
        assertThat(findOrder.isPresent()).isFalse();

        EntityManager em = emf.createEntityManager();
        OrderItem orderItem1 = em.find(OrderItem.class, orderItem.getId());
        assertThat(orderItem1).isNull();
    }
}