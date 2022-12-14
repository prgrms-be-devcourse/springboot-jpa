package kdt.springbootjpa.order;

import kdt.springbootjpa.order.entity.Item;
import kdt.springbootjpa.order.entity.Member;
import kdt.springbootjpa.order.entity.Order;
import kdt.springbootjpa.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static kdt.springbootjpa.order.entity.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void Order_저장하기1() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = Member.builder()
                .name("fortune")
                .address("address")
                .description("description")
                .build();
        entityManager.persist(member);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("memo")
                .build();

        order.setMember(member);
        //member.addOrder(order);

        entityManager.persist(order);
        transaction.commit();
        entityManager.clear();

        Order savedOrder = entityManager.find(Order.class, order.getUuid());
        Member savedMember = entityManager.find(Member.class, member.getId());
        assertThat(savedOrder.getMember().getId()).isEqualTo(savedMember.getId());
        assertThat(savedMember.getOrders().get(0).getUuid()).isEqualTo(savedOrder.getUuid());
    }

    @Test
    void Order_저장하기2() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = Member.builder()
                .name("fortune")
                .address("address")
                .description("description")
                .build();
        entityManager.persist(member);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("memo")
                .build();

        member.addOrder(order);

        entityManager.persist(order);
        transaction.commit();
        entityManager.clear();

        Order savedOrder = entityManager.find(Order.class, order.getUuid());
        Member savedMember = entityManager.find(Member.class, member.getId());
        assertThat(savedOrder.getMember().getId()).isEqualTo(savedMember.getId());
        assertThat(savedMember.getOrders().get(0).getUuid()).isEqualTo(savedOrder.getUuid());
    }

    @Test
    void OrderItem_저장하기1() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Item item = Item.builder()
                .price(1500)
                .stockQuantity(10)
                .build();
        entityManager.persist(item);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("memo")
                .build();
        entityManager.persist(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .quantity(2)
                .price(3000)
                .build();

        orderItem.setItem(item);
        orderItem.setOrder(order);

        entityManager.persist(orderItem);

        transaction.commit();
        entityManager.clear();

        OrderItem returnedOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Order returnedOrder = entityManager.find(Order.class, order.getUuid());
        Item returnItem = entityManager.find(Item.class, item.getId());

        assertThat(returnedOrderItem.getItem().getId()).isEqualTo(returnItem.getId());
        assertThat(returnedOrderItem.getOrder().getUuid()).isEqualTo(returnedOrder.getUuid());
    }

    @Test
    void OrderItem_저장하기2() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Item item = Item.builder()
                .price(1500)
                .stockQuantity(10)
                .build();
        entityManager.persist(item);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("memo")
                .build();
        entityManager.persist(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .quantity(2)
                .price(3000)
                .build();

        orderItem.setItem(item);
        order.addOrderItem(orderItem);

        entityManager.persist(orderItem);

        transaction.commit();
        entityManager.clear();

        OrderItem returnedOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Order returnedOrder = entityManager.find(Order.class, order.getUuid());
        Item returnItem = entityManager.find(Item.class, item.getId());

        assertThat(returnedOrderItem.getOrder().getUuid()).isEqualTo(returnedOrder.getUuid());
        assertThat(returnedOrderItem.getItem().getId()).isEqualTo(returnItem.getId());
    }
}
