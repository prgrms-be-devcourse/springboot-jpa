package com.example.springbootjpa.domain.order;

import com.example.springbootjpa.repository.ItemRepository;
import com.example.springbootjpa.repository.MemberRepository;
import com.example.springbootjpa.repository.OrderItemRepository;
import com.example.springbootjpa.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void cleanUp() {
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void member_insert() {
        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.persist(member);

        transaction.commit();

        em.close();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.persist(member);
        Member memberEntity = em.find(Member.class, member.getId());

        Order order = new Order(
                UUID.randomUUID().toString()
                , "부재 시 전화줘요"
                , OrderStatus.OPENED
                , LocalDateTime.now()
                , memberEntity
        );
        em.persist(order);
        transaction.commit();

        Order orderEntity = em.find(Order.class, order.getUuid()); // select Order
        //데이터 중심 설계에서는 order의 member를 구해오기 위해 다시 회원 조회가 필수
        Member orderMemberEntity = em.find(Member.class, orderEntity.getMember().getId()); //select member
        //orderEntity.getMember // 객체 중심 설계라면 이렇게 객체 그램프 탐색을 해야하지 않을까?
        assertThat(orderEntity).isEqualTo(order);
        assertThat(orderMemberEntity).isEqualTo(member);

        em.close();
    }

    @Test
    void 유저_연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        em.persist(member);

        Order order = new Order(
                UUID.randomUUID().toString()
                , "부재 시 전화줘요"
                , OrderStatus.OPENED
                , LocalDateTime.now()
                , member
        );

        em.persist(order);

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        assertThat(entity.getMember().getNickName()).isEqualTo(member.getNickName());
        assertThat(entity.getMember().getOrders()).hasSize(1);
        assertThat(order.getMember().getOrders()).hasSize(1);

        em.close();
    }

    @Test
    void 아이템_연관관계_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Member member = new Member("sanghyeok",
                "hyeok",
                25,
                "수원시",
                "안녕하세요.");

        em.persist(member);

        Order order = new Order(
                UUID.randomUUID().toString()
                , "부재 시 전화줘요"
                , OrderStatus.OPENED
                , LocalDateTime.now()
                , member
        );

        em.persist(order);

        Item item1 = new Item("test1", 5000, 12);
        Item item2 = new Item("test2", 15000, 13);

        em.persist(item1);
        em.persist(item2);

        em.persist(new OrderItem(item1.getPrice(), order.getOrderStatus(), order, item1));
        em.persist(new OrderItem(item2.getPrice(), order.getOrderStatus(), order, item2));

        transaction.commit();

        em.clear();
        Order entity = em.find(Order.class, order.getUuid());

        assertThat(entity.getOrderItems()).hasSize(2);
        assertThat(order.getOrderItems()).hasSize(2);
        assertThat(item1.getOrderItems()).hasSize(1);
        assertThat(item2.getOrderItems()).hasSize(1);

        em.close();
    }
}
