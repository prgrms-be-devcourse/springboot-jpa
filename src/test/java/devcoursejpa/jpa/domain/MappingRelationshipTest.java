package devcoursejpa.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MappingRelationshipTest {

    @Autowired
    private EntityManager em;

    private Member member;
    private Order order;
    private OrderItem orderItem;
    private Item item;

    @BeforeEach
    void initSetting() {

        member = new Member("jiwoong", "wisehero", 27, "안양", "백수임");
        em.persist(member);

        item = new Item(20000, 10);
        em.persist(item);

        order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), OrderStatus.OPENED, "개조심");
        order.setMember(member);
        orderItem = new OrderItem(20000, 10, item);
        order.addOrderItem(orderItem);
        em.persist(order);
        em.persist(orderItem);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("Member(1) - Order(N) 양방향 연관관계 설정")
    void testRelationshipMemberAndOrder() {
        Order order1 = em.find(Order.class, order.getUuid());
        Member member1 = em.find(Member.class, member.getId());

        assertThat(order1.getMember()).isSameAs(member1);
    }

    @Test
    @DisplayName("Order(1) - OrderItem(N) 양방향 연관관계 설정")
    void testRelationshipOrderAndOrderItem() {
        Order order1 = em.find(Order.class, order.getUuid());
        OrderItem orderItem1 = em.find(OrderItem.class, orderItem.getId());

        assertThat(order1.getOrderItems().get(0)).isSameAs(orderItem1);
    }

    @Test
    @DisplayName("OrderItem(N) - Item(1) 양방향 연관관계 설정")
    void testRelationshipOrderItemAndItem() {
        OrderItem orderItem1 = em.find(OrderItem.class, orderItem.getId());
        Item item1 = em.find(Item.class, item.getId());

        assertThat(orderItem1.getItem()).isSameAs(item1);
    }
}