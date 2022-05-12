package prgrms.assignment.jpa.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgrms.assignment.jpa.domain.member.Member;
import prgrms.assignment.jpa.domain.order.Item;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static prgrms.assignment.jpa.domain.order.Order.createOrder;
import static prgrms.assignment.jpa.domain.order.OrderItem.createOrderItem;

@SpringBootTest
public class OrderMappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("연관관계 매핑 테스트 🚀")
    public void testMapping() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();

        var member = new Member("kim", "nick-kim", 10, "istanbul", "this is member.");
        em.persist(member);

        var item1 = new Item(10, 10);
        var item2 = new Item(20, 20);
        em.persist(item1);
        em.persist(item2);

        var orderItem1 = createOrderItem(10, 1, item1);
        var orderItem2 = createOrderItem(20, 1, item2);

        var order = createOrder("memo", member, orderItem1, orderItem2);
        em.persist(order);

        transaction.commit();

        em.clear();

        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getOrderItems().get(0)).isEqualTo(orderItem1);
        assertThat(order.getOrderItems().get(1)).isEqualTo(orderItem2);
    }
}
