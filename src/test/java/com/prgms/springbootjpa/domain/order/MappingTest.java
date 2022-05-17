package com.prgms.springbootjpa.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgms.springbootjpa.domain.order.item.Food;
import com.prgms.springbootjpa.domain.order.item.Item;
import com.prgms.springbootjpa.domain.order.vo.Name;
import com.prgms.springbootjpa.domain.order.vo.NickName;
import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class MappingTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private Member member;

    private Item item;

    private Order order;

    private OrderItem orderItem;

    @BeforeAll
    void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        member = new Member(new Name("test"), new NickName("test"), 26, "강남", null);
        em.persist(member);

        item = new Food(new Price(2000), new Quantity(100), "백종원");
        em.persist(item);

        order = new Order("부재시 연락주세요.");
        order.assignMember(member);
        orderItem = new OrderItem(new Price(2000), new Quantity(2), item);
        order.addOrderItem(orderItem);
        em.persist(order);

        em.getTransaction().commit();
        em.close();
    }

    @BeforeEach
    void set() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void clean() {
        em.close();
    }

    @Test
    @DisplayName("Member, Order 연관관계 테스트")
    void testMemberAndOrderMapping() {
        //Given, When
        var findOrder = em.find(Order.class, order.getId());
        var findMember = em.find(Member.class, this.member.getId());

        //Then
        assertThat(findOrder.getMember()).isSameAs(findMember);
    }

    @Test
    @DisplayName("Order, OrderItem 연관관계 테스트")
    void testOrderAndOrderItemMapping() {
        //Given, When
        var findOrder = em.find(Order.class, this.order.getId());
        var findOrderItem = em.find(OrderItem.class, this.orderItem.getId());

        //Then
        assertThat(findOrder.getOrderItems().get(0)).isSameAs(findOrderItem);
    }

    @Test
    @DisplayName("OrderItem, Item 연관관계 테스트")
    void testOrderItemAndItemMapping() {
        //Given, When
        var findOrderItem = em.find(OrderItem.class, this.orderItem.getId());
        var findItem = em.find(Item.class, this.item.getId());

        //Then
        assertThat(findOrderItem.getItem()).isSameAs(findItem);
    }
}
