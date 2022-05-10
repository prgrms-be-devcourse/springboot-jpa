package org.programmers.kdt.studyjpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.programmers.kdt.studyjpa.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void insert_member() {
        var member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구 만움직이면 쏜다");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("back-end developer :)");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void test_mapping() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAddress("서울시 동작구 만움직이면 쏜다");
        member.setAge(33);
        member.setDescription("KDT 화이팅");

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재시 연락바람");
        order.setMember(member);

        entityManager.persist(order);
        transaction.commit();

        entityManager.clear();
        var entity = entityManager.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());
    }

    @Test
    void test_mapping_order_and_orderItem() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();

        Item chicken = new Item();
        chicken.setPrice(16000);
        chicken.setStockQuantity(5);
        chicken.setOrderItems(new ArrayList<>());
        entityManager.persist(chicken);

        Item coke = new Item();
        coke.setPrice(3000);
        coke.setStockQuantity(3);
        coke.setOrderItems(new ArrayList<>());
        entityManager.persist(coke);

        OrderItem orderChickens = new OrderItem();
        orderChickens.setItem(chicken);
        orderChickens.setPrice(32000);
        orderChickens.setQuantity(2);

        OrderItem orderCokes = new OrderItem();
        orderCokes.setItem(coke);
        orderCokes.setPrice(6000);
        orderCokes.setQuantity(3);

        entityManager.persist(orderChickens);
        entityManager.persist(orderCokes);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("애기 먹을건데 치즈볼 하나 낭낭하게 챙겨주세요~");
        order.setOrderItems(Lists.newArrayList(orderChickens, orderCokes));

        entityManager.persist(order);

        transaction.commit();

        //Order 확인
        var findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder).isEqualTo(order);

        //Order의 OrderItem 확인
        var orderItems = findOrder.getOrderItems();
        assertThat(orderItems).hasSize(2).contains(orderChickens, orderCokes);

        //OrderItem의 Item 확인
        var getChicken = orderItems.get(0).getItem();
        var getCoke = orderItems.get(1).getItem();
        assertThat(getChicken).isEqualTo(chicken);
        assertThat(getCoke).isEqualTo(coke);
    }
}
