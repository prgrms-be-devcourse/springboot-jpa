package com.example.demo.persistence;

import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Slf4j
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void order만가지고_member_orderItem_item을가져올수있다() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Member member = new Member("yerim", "yerimKing", 10, "jj street", "hi i'm yerim");
        Item item = new Item("고양이티셔츠", 30000L, 3 );
        Order order = new Order("택배실보관",member);
        OrderItem orderItem = new OrderItem(3, order, item);
        order.addOrderItem(orderItem);

        entityManager.persist(member);
        entityManager.persist(item);
        entityManager.persist(orderItem);
        entityManager.persist(order);

        transaction.commit();

        entityManager.detach(order);
        Order returnOrder = entityManager.find(Order.class, order.getUuid());

        assertAll(
                () -> assertThat(returnOrder).usingRecursiveComparison().isEqualTo(order),
                () -> assertThat(returnOrder.getMember()).usingRecursiveComparison().isEqualTo(member),
                () -> assertThat(returnOrder.getOrderItems().get(0)).usingRecursiveComparison().isEqualTo(orderItem),
                () -> assertThat(returnOrder.getOrderItems().get(0).getItem()).usingRecursiveComparison().isEqualTo(item)
        );
    }
}
