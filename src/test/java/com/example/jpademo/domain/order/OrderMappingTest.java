package com.example.jpademo.domain.order;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.jpademo.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class OrderMappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("jinuk");
        member.setAddress("서울 동작");
        member.setAge(26);
        member.setNickName("uk");
        member.setDescription("개발자가 되고싶은 욱");

        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재시 연락");
        order.setMember(member);

        em.persist(order);

        Item item = new Item();
        item.setPrice(1000);
        item.setStackQuantity(100);
        em.persist(item);

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(2000);
        orderItem.setQuantity(2);
        orderItem.setOrder(order);
        orderItem.setItem(item);
        em.persist(orderItem);

        transaction.commit();

        // 데이터베이스에서 가져오기 위해 영속성 제거
        em.detach(orderItem);
        orderItem = em.find(OrderItem.class, orderItem.getId());

        // orderItem - order 연관 관계
        assertThat(orderItem.getOrderId()).isEqualTo(order.getUuid());

        // orderItem - item 연관 관계
        assertThat(orderItem.getItemId()).isEqualTo(item.getId());

        // order - member 연관 관계
        em.detach(order);
        order = em.find(Order.class, order.getUuid());
        assertThat(order.getMemberId()).isEqualTo(member.getId());
    }
}
