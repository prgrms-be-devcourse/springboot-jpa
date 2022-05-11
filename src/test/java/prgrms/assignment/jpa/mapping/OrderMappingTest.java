package prgrms.assignment.jpa.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgrms.assignment.jpa.domain.member.Member;
import prgrms.assignment.jpa.domain.order.Item;
import prgrms.assignment.jpa.domain.order.Order;
import prgrms.assignment.jpa.domain.order.OrderItem;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderMappingTest {

    @Autowired
    EntityManagerFactory emf;

    Long orderId;
    Long itemId;

    @BeforeEach
    public void setup() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();
        transaction.begin();

        var member = new Member("kim", "nick-kim", 10, "istanbul", "this is member.");
        em.persist(member);

        var order = new Order("this is new order.");
        order.setMember(member);
        em.persist(order);

        var item = new Item(10, 10);
        em.persist(item);

        orderId = order.getId();
        itemId = item.getId();

        transaction.commit();
    }

    @Test
    @DisplayName("연관관계 매핑을 하여 OrderItem 을 통해 Order, Item 을 조회할 수 있다.")
    public void testMapping() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();

        var order = em.find(Order.class, orderId);
        var item = em.find(Item.class, itemId);

        var orderItem = new OrderItem(10, 1);
        orderItem.setOrder(order);
        orderItem.setItem(item);

        em.persist(orderItem);

        transaction.commit();

        var retrievedOrder = orderItem.getOrder();
        var retrievedItem = orderItem.getItem();

        assertThat(retrievedOrder).isEqualTo(order);
        assertThat(retrievedItem).isEqualTo(item);
    }
}
