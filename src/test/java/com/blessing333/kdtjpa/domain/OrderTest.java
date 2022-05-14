package com.blessing333.kdtjpa.domain;

import com.blessing333.kdtjpa.domain.repository.ItemRepository;
import com.blessing333.kdtjpa.domain.repository.MemberRepository;
import com.blessing333.kdtjpa.domain.repository.OrderItemRepository;
import com.blessing333.kdtjpa.domain.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Slf4j
class OrderTest {
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private MemberRepository memberRepository;
    private EntityManager em;
    private final Member member = Member.builder()
            .address("경기도 성남시 분당구")
            .name("tester")
            .nickName("blessing")
            .build();

    @BeforeEach
    void initEntityManager() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void resetData() {
        log.warn("reset all data after test");
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("Order 영속화시, OrderItem도 같이 영속화한다. (Cascade Persist Test)")
    @Test
    void cascadePersistTest() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(member);
        Item item = Item.createNewItem(20000, 50);
        em.persist(item);
        Order order = Order.createOrder(LocalDateTime.now(), OrderStatus.OPENED, "memo", member);
        em.persist(order);
        OrderItemId orderItemId = new OrderItemId(order.getId(), item.getId());
        OrderItem orderItem = OrderItem.createNewOrderItem(orderItemId, order, item);
        order.getOrderItems().add(orderItem);
        transaction.commit();
        em.clear();

        OrderItem foundOrderItem = em.find(OrderItem.class, orderItemId);

        assertNotNull(foundOrderItem);
        assertThat(foundOrderItem.getOrder()).isEqualTo(orderItem.getOrder());
        assertThat(foundOrderItem.getItem()).isEqualTo(orderItem.getItem());
    }

    @DisplayName("Order.orderItems에서 OrderItem 삭제 시, DB에서 삭제된다. (Cascade Remove Test)")
    @Test
    void cascadeRemoveTest() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(member);
        Item item = Item.createNewItem(20000, 50);
        em.persist(item);
        Order order = Order.createOrder(LocalDateTime.now(), OrderStatus.OPENED, "memo", member);
        em.persist(order);
        Long orderId = order.getId();
        OrderItemId orderItemId = new OrderItemId(order.getId(), item.getId());
        OrderItem orderItem = OrderItem.createNewOrderItem(orderItemId, order, item);
        order.getOrderItems().add(orderItem);
        transaction.commit();
        em.clear();

        transaction.begin();
        Order foundOrder = em.find(Order.class, orderId);
        foundOrder.getOrderItems().remove(orderItem);
        transaction.commit();
        em.clear();

        assertNull(em.find(OrderItem.class, orderItemId));
    }

    @DisplayName("Order 삭제 시 연관된 모든 OrderItem이 DB에서 삭제된다(orphanRemoval)")
    @Test
    void orphanRemovalTest() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(member);
        Item item = Item.createNewItem(20000, 50);
        Item item2 = Item.createNewItem(50000, 100);
        em.persist(item);
        em.persist(item2);
        Order order = Order.createOrder(LocalDateTime.now(), OrderStatus.OPENED, "memo", member);
        em.persist(order);
        Long orderId = order.getId();
        OrderItemId orderItemId = new OrderItemId(order.getId(), item.getId());
        OrderItemId orderItemId2 = new OrderItemId(order.getId(), item2.getId());
        OrderItem orderItem = OrderItem.createNewOrderItem(orderItemId, order, item);
        OrderItem orderItem2 = OrderItem.createNewOrderItem(orderItemId2, order, item2);
        order.getOrderItems().add(orderItem);
        order.getOrderItems().add(orderItem2);
        transaction.commit();
        em.clear();

        transaction.begin();
        Order found = em.find(Order.class, orderId);
        em.remove(found);
        transaction.commit();
        em.clear();

        assertNull(em.find(OrderItem.class, orderItemId));
        assertNull(em.find(OrderItem.class, orderItemId2));
    }

    @DisplayName("Order 조회시 멤버도 같이 조회 되어야한다")
    @Test
    void test() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Order order = Order.createOrder(LocalDateTime.now(), OrderStatus.OPENED, "memo", member);
        em.persist(member);
        em.persist(order);
        transaction.commit();
        Long orderId = order.getId();
        em.clear();

        Order savedOrder = em.find(Order.class, orderId);

        Member orderer = savedOrder.getMember();
        assertThat(orderer.getAddress()).isEqualTo(member.getAddress());
        assertThat(orderer.getName()).isEqualTo(member.getName());
        assertThat(orderer.getNickName()).isEqualTo(member.getNickName());
    }

    @DisplayName("Order 조회시 OrderItem도 같이 조회되어야 한다")
    @Test
    void orderWithOrderItemTest() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(member);
        Item item = Item.createNewItem(20000, 50);
        em.persist(item);
        Order order = Order.createOrder(LocalDateTime.now(), OrderStatus.OPENED, "memo", member);
        em.persist(order);
        Long orderId = order.getId();
        OrderItemId orderItemId = new OrderItemId(order.getId(), item.getId());
        OrderItem orderItem = OrderItem.createNewOrderItem(orderItemId, order, item);
        order.getOrderItems().add(orderItem);
        transaction.commit();
        em.clear();

        Order foundOrder = em.find(Order.class, orderId);
        List<OrderItem> lazyLoadedOrderItems = foundOrder.getOrderItems();
        OrderItem foundOrderItem = lazyLoadedOrderItems.get(0);

        assertThat(lazyLoadedOrderItems).hasSize(1);
        assertThat(foundOrderItem.getId()).isEqualTo(orderItem.getId());
        assertThat(foundOrderItem.getItem()).isEqualTo(orderItem.getItem());
        assertThat(foundOrderItem.getOrder()).isEqualTo(orderItem.getOrder());
    }
}