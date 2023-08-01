package prgrms.lecture.jpa.domain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.validation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomainRelationsTests {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager em;
    private EntityTransaction transaction;
    private Validator validator;

    @BeforeEach
    public void startMethod() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    public void finishMethod() {
        transaction.rollback();
        em.close();
    }


    private Member getMemberData() {
        Member member = new Member("lee", "leeNickName", 30, "lee입니다.", "대한민국");
        em.persist(member);
        return member;
    }

    private Order getOrderData(Member member) {
        Order order = new Order(UUID.randomUUID().toString(), "Test order", OrderStatus.OPENED, LocalDateTime.now(), member);
        em.persist(order);
        return order;
    }

    private Item getItemData() {
        Item item = new Item(1000L, 50L);
        em.persist(item);
        return item;
    }

    private OrderItem getOrderItemData(Order order, Item item) {
        OrderItem orderItem = new OrderItem(item.getPrice(), 3,  order, item);
        em.persist(orderItem);
        return orderItem;
    }


    @Test
    @DisplayName("[영속성 컨텍스트]: 회원-주문 연관관계")
    public void memberAndOrdersFromEM() {
        // Given
        Member member = getMemberData();
        Order order1 = getOrderData(member);
        Order order2 = getOrderData(member);

        // When
        Member foundMember = em.find(Member.class, member.getId());

        // Then
        assertThat(foundMember.getOrders()).hasSize(2);
        List<String> orderIds = foundMember.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());
        assertThat(orderIds).containsExactlyInAnyOrder(order1.getId(), order2.getId());
    }

    @Test
    @DisplayName("[DB]: 회원-주문 연관관계")
    public void memberAndOrdersFromDB() {
        // Given
        Member member = getMemberData();
        Order order1 = getOrderData(member);
        Order order2 = getOrderData(member);

        // When
        em.flush();
        em.clear();
        Member foundMember = em.find(Member.class, member.getId());

        // Then
        assertThat(foundMember.getOrders()).hasSize(2);
        List<String> orderIds = foundMember.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());
        assertThat(orderIds).containsExactlyInAnyOrder(order1.getId(), order2.getId());
    }

    @Test
    @DisplayName("[영속성 컨텍스트]: 주문-회원 연관관계")
    public void orderAndMemberInEM() {
        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);

        // When
        Order foundOrder = em.find(Order.class, order.getId());

        // Then
        assertThat(foundOrder.getMember()).isEqualTo(member);
        assertThat(foundOrder.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("[DB]: 주문-회원 연관관계")
    public void orderAndMemberInDB() {
        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);

        // When
        em.flush();
        em.clear();
        Order foundOrder = em.find(Order.class, order.getId());

        // Then
        assertThat(foundOrder.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("[영속성 컨텍스트]: 주문-주문아이템 연관관계")
    public void orderAndOrderItemsFromEM() {

        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);
        Item item = getItemData();
        OrderItem orderItem1 = getOrderItemData(order, item);
        OrderItem orderItem2 = getOrderItemData(order, item);

        // When
        Order foundOrder = em.find(Order.class, order.getId());

        // Then
        List<OrderItem> orderItems = foundOrder.getOrderItems();
        assertThat(orderItems).hasSize(2);
        assertThat(orderItems).containsExactlyInAnyOrder(orderItem1, orderItem2);
        assertThat(orderItems.get(0).getOrder()).isEqualTo(foundOrder);
        assertThat(orderItems.get(1).getOrder()).isEqualTo(foundOrder);

    }

    @Test
    @DisplayName("[DB]: 주문-주문아이템 연관관계")
    public void orderAndOrderItemsFromDB() {

        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);
        Item item = getItemData();
        OrderItem orderItem1 = getOrderItemData(order, item);
        OrderItem orderItem2 = getOrderItemData(order, item);

        // When
        em.flush();
        em.clear();
        Order foundOrder = em.find(Order.class, order.getId());
        List<OrderItem> orderItems = foundOrder.getOrderItems();

        // Then
        assertThat(orderItems).hasSize(2);
        List<Long> orderItemIds = orderItems.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());
        assertThat(orderItemIds).containsExactlyInAnyOrder(orderItem1.getId(), orderItem2.getId());
        assertThat(orderItems.get(0).getOrder()).isEqualTo(foundOrder);
        assertThat(orderItems.get(1).getOrder()).isEqualTo(foundOrder);
    }

    @Test
    @DisplayName("[영속성 컨텍스트]: 주문아이템-주문 연관관계")
    public void orderItemAndOrderInEM() {

        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);
        Item item = getItemData();
        OrderItem orderItem = getOrderItemData(order, item);

        // When
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        // Then
        assertThat(foundOrderItem.getOrder()).isEqualTo(order);
        assertThat(foundOrderItem.getOrder().getId()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("[DB]: 주문아이템-주문 연관관계")
    public void orderItemAndOrderInDB() {
        // Given
        Member member = getMemberData();
        Order order = getOrderData(member);
        Item item = getItemData();
        OrderItem orderItem = getOrderItemData(order, item);

        // When
        em.flush();
        em.clear();
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        // Then
        assertThat(foundOrderItem.getOrder().getId()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("[영속성 컨텍스트]: 주문아이템-아이템 연관관계")
    public void orderItemAndItemFromEM() {
        // Given
        Member member = getMemberData();
        Item item = getItemData();
        Order order = getOrderData(member);
        OrderItem orderItem = getOrderItemData(order, item);

        // When
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        // Then
        assertThat(foundOrderItem.getItem()).isNotNull();
        assertThat(foundOrderItem.getItem().getId()).isEqualTo(item.getId());
    }

    @Test
    @DisplayName("[DB]: 주문아이템-아이템 연관관계")
    public void orderItemAndItemFromDB() {
        // Given
        Member member = getMemberData();
        Item item = getItemData();
        Order order = getOrderData(member);
        OrderItem orderItem = getOrderItemData(order, item);

        // When
        em.flush();
        em.clear();
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        // Then
        assertThat(foundOrderItem.getItem()).isNotNull();
        assertThat(foundOrderItem.getItem().getId()).isEqualTo(item.getId());
    }

}
