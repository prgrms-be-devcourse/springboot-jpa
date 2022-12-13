package grepp.prgrms.springbootjpa.domain.entityMapping;

import grepp.prgrms.springbootjpa.domain.item.Food;
import grepp.prgrms.springbootjpa.domain.item.Item;
import grepp.prgrms.springbootjpa.domain.member.Member;
import grepp.prgrms.springbootjpa.domain.order.Order;
import grepp.prgrms.springbootjpa.domain.order.OrderStatus;
import grepp.prgrms.springbootjpa.domain.orderitem.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
public class EntityMappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    public void 사용자가_주문_생성(){
        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = new Member();
        member.setAge(20);
        member.setNickName("hwankim123");
        member.setName("김환");
        member.setAddress("경기도 성남시 어쩌구");
        member.setDescription("김환입니다");
        member.setCreatedAt(LocalDateTime.now());
        member.setCreatedBy("hwankim123");;

        entityManager.persist(member);

        //when
        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("맛있는 라면을 삽시다");
        order.setUuid(uuid);

        Member findMember = entityManager.find(Member.class, member.getId());
        findMember.createOrder(order);

        log.info("transaction committed");
        transaction.commit();

        //then
        Order findOrder = entityManager.find(Order.class, uuid);
        Member memberOrdered = findOrder.getMember();
        assertThat(memberOrdered.getName()).isEqualTo("김환");

    }

    @Test
    public void 상품까지_연결된_주문_생성(){
        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Item item = new Food();
        item.setPrice(1000);
        item.setStockQuantity(20);

        entityManager.persist(item);

        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("맛있는 라면을 삽시다");

        entityManager.persist(order);

        //when
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(12);
        order.addOrderItem(orderItem);

        entityManager.persist(order);

        transaction.commit();
        log.info("transaction committed");

        //then
        Order findOrder = entityManager.find(Order.class, uuid);
        List<OrderItem> orderItems = findOrder.getOrderItems();
        assertThat(orderItems.size()).isEqualTo(1);
    }

    @Test
    public void 영속성_삭제_전이(){
        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Item item = new Food();
        item.setPrice(1000);
        item.setStockQuantity(20);

        entityManager.persist(item);

        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("맛있는 라면을 삽시다");

        entityManager.persist(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(12);
        order.addOrderItem(orderItem);
        entityManager.persist(order);

        transaction.commit();
        Long orderItemId = orderItem.getId();
        log.info("transaction committed");

        //when
        transaction.begin();

        Order findOrder = entityManager.find(Order.class, uuid);
        entityManager.remove(findOrder);

        transaction.commit();
        log.info("transaction committed");

        //then
        Order findAfterDeleteOrder = entityManager.find(Order.class, uuid);
        assertThat(findAfterDeleteOrder).isEqualTo(null);
        OrderItem findAfterDeleteOrderItem = entityManager.find(OrderItem.class, orderItemId);
        assertThat(findAfterDeleteOrderItem).isEqualTo(null);
    }

    @Test
    public void 고아객체_삭제(){
        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Item item = new Food();
        item.setPrice(1000);
        item.setStockQuantity(20);

        entityManager.persist(item);

        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("맛있는 라면을 삽시다");

        entityManager.persist(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(12);
        order.addOrderItem(orderItem);
        entityManager.persist(order);

        transaction.commit();
        Long orderItemId = orderItem.getId();
        log.info("transaction committed");

        //when
        transaction.begin();
        order.getOrderItems().remove(orderItem);
        transaction.commit();
        log.info("transaction committed");

        //then
        OrderItem deletedOrderItem = entityManager.find(OrderItem.class, orderItemId);
        assertThat(deletedOrderItem).isEqualTo(null);

    }
}
