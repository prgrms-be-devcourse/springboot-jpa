package kdt.springbootjpa.order;

import kdt.springbootjpa.order.entity.Member;
import kdt.springbootjpa.order.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static kdt.springbootjpa.order.entity.OrderStatus.OPENED;

@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void 저장하기_단방향관계() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = Member.builder()
                .name("fortune")
                .address("address")
                .description("description")
                .build();
        entityManager.persist(member);

        Order order = Order.builder()
                .uuid(UUID.randomUUID().toString())
                .orderStatus(OPENED)
                .orderDatetime(LocalDateTime.now())
                .memo("memo")
                .build();
        order.setMember(member);
        entityManager.persist(order);

        transaction.commit();
        entityManager.clear();
    }
}
