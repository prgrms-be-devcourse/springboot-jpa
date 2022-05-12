package org.prgrms.springjpa.domain.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager em;

    EntityTransaction transaction;

    Member member;

    Item item;

    OrderItem orderItem;

    Order order;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();

        member = Member.builder()
                .name("박우진")
                .age(27)
                .nickName("빡구진")
                .address("경기도 고양시 일산동구")
                .description("백수임니다")
                .build();
        item = Item.builder()
                .price(10000)
                .stockQuantity(20)
                .build();

        orderItem = OrderItem.createOrderItem(item, 10000, 10);
        order = Order.createOrder(member, "첫 주문입니다.", orderItem);

        transaction.begin();
        em.persist(member);
        em.persist(item);
        em.persist(order);
        transaction.commit();
    }

    @AfterEach
    void tearDown() {
        transaction.begin();
        em.createQuery("DELETE FROM OrderItem ").executeUpdate();
        em.createQuery("DELETE FROM Order ").executeUpdate();
        em.createQuery("DELETE FROM Member ").executeUpdate();
        em.createQuery("DELETE FROM Item ").executeUpdate();
        transaction.commit();
    }

    @Test
    void 주문을_생성후_조회할_수_있다() {
        //given
        //when
        em.clear();
        Order findOrder = em.find(Order.class, order.getId());
        //then
        assertThat(findOrder.getId()).usingRecursiveComparison().isEqualTo(order.getId());
        assertThat(findOrder.getOrderItems().get(0).getId()).isEqualTo(orderItem.getId());
        assertThat(findOrder.getOrderItems().get(0).getItem().getId()).isEqualTo(item.getId());
        assertThat(findOrder.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void 회원의_주문내역을_조회할수있다() {
        //given
        //when
        em.clear();
        Member findMember = em.find(Member.class, member.getId());
        //then
        assertThat(findMember.getOrders().get(0).getId()).usingRecursiveComparison().isEqualTo(order.getId());
    }

    @Test
    void 아이템을_통해_주문아이템을_조회할수있다() throws Exception {
        //given
        //when
        em.clear();
        Item findItem = em.find(Item.class, item.getId());
        //then
        assertThat(findItem.getOrderItems().get(0).getId()).isEqualTo(orderItem.getId());
    }

    @Test
    void 주문아이템을_통해_주문을_조회할_수_있다() throws Exception {
        //given
        //when
        em.clear();
        OrderItem findOrderItem = em.find(OrderItem.class, this.orderItem.getId());
        //then
        assertThat(findOrderItem.getOrder().getId()).isEqualTo(order.getId());
    }

    @Test
    void 주문을_삭제하면_주문아이템도_삭제된다() throws Exception {
        //given
        //when
        transaction.begin();
        em.remove(order);
        transaction.commit();
        Order findOrder = em.find(Order.class, order.getId());
        OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());
        //then
        assertThat(findOrder).isNull();
        assertThat(findOrderItem).isNull();
    }
}
