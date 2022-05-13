package com.prgrms.lec_jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@Slf4j
public class OrderPersistentTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void save_test() {

        Member member = Member.builder()
                .name("jahee")
                .address("home")
                .age(28)
                .description("whiteHands")
                .nickName("jj")
                .createdBy("jaehee")
                .build();

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(member);
        transaction.commit();
    }

    @Test
    void associate_test() {

        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = Member.builder()
                .name("jaehee")
                .address("home")
                .age(28)
                .description("whiteHands")
                .nickName("jj")
                .createdBy("jaehee")
                .build();

        em.persist(member);

        Item item = Item.builder()
                .price(1000)
                .stockQuantity(100)
                .createdBy("jaehee")
                .build();

        em.persist(item);

        Order order = Order.builder()
                .memo("Car Order")
                .createdBy("jaehee")
                .build();

        order.setMember(member);

        em.persist(order);

        OrderItem orderItem = OrderItem.builder()
                .price(1000)
                .quantity(3)
                .createdBy("jaehee")
                .build();

        orderItem.setItem(item);
        orderItem.setOrder(order);

        em.persist(orderItem);

        transaction.commit();

        //when
        OrderItem entity = em.find(OrderItem.class, orderItem.getId());

        String memberNameExpected = entity.getOrder().getMember().getName();
        long itemPriceExpected = entity.getItem().getPrice();
        String orderMemoExpected = entity.getOrder().getMemo();

        //then
        Assertions.assertThat(memberNameExpected).isEqualTo(member.getName());
        Assertions.assertThat(itemPriceExpected).isEqualTo(item.getPrice());
        Assertions.assertThat(orderMemoExpected).isEqualTo(order.getMemo());
    }
}
