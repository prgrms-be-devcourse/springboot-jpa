package devcourse.jpa;

import devcourse.jpa.domain.item.Item;
import devcourse.jpa.domain.member.Member;
import devcourse.jpa.domain.order.Order;
import devcourse.jpa.domain.orderitem.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EntityMappingTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Member member;

    private Item item;

    @BeforeEach
    void createDummyData() {
        this.member = new Member("sample-name", 27);
        this.item = new Item("춘식이", 25000, 25);
    }

    @Test
    @DisplayName("연관관계 매핑 확인")
    void test_mapping() {
        // given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(this.member);
        em.persist(this.item);

        Order order = new Order(member);
        em.persist(order);

        // when
        OrderItem orderItem = new OrderItem(this.item);
        orderItem.enrollOrder(order);
        em.persist(orderItem);

        transaction.commit();

        // then
        OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());
        Order actualOrder = findOrderItem.getOrder();

        assertThat(actualOrder == order).isTrue();
    }
}
