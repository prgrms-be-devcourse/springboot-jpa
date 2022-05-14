package com.kdt.lecture.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderDomainTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;
    EntityTransaction entityTransaction;
    Order order;
    Member member;
    OrderItem orderItem;
    Item item;

    @BeforeEach
    void entityPersist() {
        entityManager = emf.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        member = EntityRegister.member(entityManager);
        order = EntityRegister.order(entityManager);
        orderItem = EntityRegister.orderItem(entityManager);
        item = EntityRegister.item(entityManager);
        order.setMember(member);

        orderItem.addItem(item);
        order.addOrderItems(orderItem);
        member.addOrder(order);
        entityTransaction.commit();
    }

    @Test
    @DisplayName("멤버를 통해 주문, 주문 상품, 상품 조회")
    void memberToOrderFindTest() {
        Member memberEntity = entityManager.find(Member.class, this.member.getId());
        Order orderEntity = memberEntity.getOrders().get(0);
        OrderItem orderItemEntity = orderEntity.getOrderItems().get(0);
        Item itemEntity = orderItemEntity.getItems().get(0);

        entityManager.clear();

        assertThat(memberEntity).usingRecursiveComparison().isEqualTo(member);
        assertThat(orderEntity).usingRecursiveComparison().isEqualTo(order);
        assertThat(orderItemEntity).usingRecursiveComparison().isEqualTo(orderItem);
        assertThat(itemEntity).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    @DisplayName("멤버를 통해 주문, 주문 상품, 상품 업데이트")
    void updateTest() {
        Member memberEntity = entityManager.find(Member.class, member.getId());
        Order orderEntity = memberEntity.getOrders().get(0);
        OrderItem orderItemEntity = orderEntity.getOrderItems().get(0);
        Item itemEntity = orderItemEntity.getItems().get(0);

        entityTransaction.begin();
        memberEntity.setName("yongcheolkim");
        orderEntity.setOrderStatus(OrderStatus.CANCELED);
        orderItemEntity.setQuantity(6);
        itemEntity.setPrice(2000);
        entityTransaction.commit();

        entityManager.clear();

        memberEntity = entityManager.find(Member.class, member.getId());
        orderEntity = entityManager.find(Order.class, order.getId());
        orderItemEntity = entityManager.find(OrderItem.class, orderItem.getId());
        itemEntity = entityManager.find(Item.class, item.getId());

        assertThat(memberEntity.getName()).isEqualTo("yongcheolkim");
        assertThat(orderEntity.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(orderItemEntity.getQuantity()).isEqualTo(6);
        assertThat(itemEntity.getPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("회원, 주문, 주문 상품, 상품 삭제")
    void deleteTest() {
        entityTransaction.begin();
        entityManager.remove(member);
        entityManager.remove(order);
        entityManager.remove(orderItem);
        entityManager.remove(item);
        entityTransaction.commit();

        Member memberEntity = entityManager.find(Member.class, member.getId());
        Order orderEntity = entityManager.find(Order.class, order.getId());
        OrderItem orderItemEntity = entityManager.find(OrderItem.class, orderItem.getId());
        Item itemEntity = entityManager.find(Item.class, item.getId());

        assertThat(memberEntity).isNull();
        assertThat(orderEntity).isNull();
        assertThat(orderItemEntity).isNull();
        assertThat(itemEntity).isNull();
    }
}
