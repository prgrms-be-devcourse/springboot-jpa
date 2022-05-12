package com.prgms.mission_jpa.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

import static com.prgms.mission_jpa.testUtils.TestDomainCreatorUtils.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    EntityManager em;
    EntityTransaction transaction;

    @BeforeEach
    void setup(){
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @AfterEach
    void cleanup(){
        em.close();
    }

    @Nested
    @DisplayName("회원과 주문의 양방향 관계 테스트")
    class MemberAndOrderMapping {
        Member member;
        Order order;

        @BeforeEach
        void setup(){
            member = createMember();
            order = createOrder();
            transaction.begin();
            em.persist(order);
            em.persist(member);
            order.setMember(member);
            transaction.commit();
            em.clear();
        }

        @AfterEach
        void cleanup(){
            transaction.begin();
            deleteMemberForAfterEach(member);
            deleteOrderForAfterEach(order);
            transaction.commit();
            em.close();
        }

        @Test
        @DisplayName("회원을 통해 주문을 조회할 수 있다.")
        void findOrderFromMemberTest() {
            //given
            //when
            Member findMember = em.find(Member.class, member.getId());
            List<Order> orders = findMember.getOrders();
            //then
            assertThat(orders).isNotEmpty();
            assertThat(orders.get(0)).usingRecursiveComparison()
                    .ignoringFieldsOfTypes(LocalDateTime.class)
                    .isEqualTo(order);
        }

        @Test
        @DisplayName("회원을 삭제하면 주문도 같이 삭제가 된다.")
        void deleteOrderByDeletedMemberTest() {
            //given
            //when
            transaction.begin();
            Member savedMember = em.find(Member.class, member.getId());
            em.remove(savedMember);
            transaction.commit();
            //then
            Order removedOrder = em.find(Order.class, order.getUuid());
            assertThat(removedOrder).isNull();
        }

        @Test
        @DisplayName("주문이 삭제되어도 회원은 남아있다.")
        void notDeleteOrder() {
            //given
            //when
            transaction.begin();
            Order savedOrder = em.find(Order.class, order.getUuid());
            em.remove(savedOrder);
            transaction.commit();
            //then
            Member savedMember = em.find(Member.class, member.getId());
            assertThat(savedMember).isNotNull();
        }
    }

    @Nested
    @DisplayName("연관관계 삭제 테스트")
    class AllMappingTest{
        Member member;
        Order order;
        OrderItem orderItem;
        Item item;

        @BeforeEach
        void setup(){
            member = createMember();
            order = createOrder();
            orderItem = createOrderItem();
            item = createItem();

            transaction.begin();
            em.persist(order);
            em.persist(member);
            order.setMember(member);
            em.persist(item);
            em.persist(orderItem);
            orderItem.setOrder(order);
            orderItem.setItem(item);
            transaction.commit();
            em.clear();
        }

        @AfterEach
        void cleanup(){
            transaction.begin();
            deleteMemberForAfterEach(member);
            deleteOrderForAfterEach(order);
            deleteOrderItemForAfterEach(orderItem);
            deleteItemForAfterEach(item);
            transaction.commit();
            em.close();
        }

        @Test
        @DisplayName("회원을 제거하면 주문상품도 제거된다.")
        void deleteOrderItemByDeletedMember() {
            //given
            //when
            transaction.begin();
            Member savedMember = em.find(Member.class, this.member.getId());
            em.remove(savedMember);
            transaction.commit();
            //then
            OrderItem savedOrderItem = em.find(OrderItem.class, this.orderItem.getId());
            assertThat(savedOrderItem).isNull();
        }

        @Test
        @DisplayName("주문을 제거하면 주문상품도 제거된다.")
        void deleteOrderItemByDeletedOrder() {
            //given
            //when
            transaction.begin();
            Order savedOrder = em.find(Order.class, this.order.getUuid());
            em.remove(savedOrder);
            transaction.commit();
            //then
            OrderItem savedOrderItem = em.find(OrderItem.class, this.orderItem.getId());
            assertThat(savedOrderItem).isNull();
        }

        @Test
        @DisplayName("상품을 제거하면 주문상품도 제거된다.")
        void deleteOrderItemByDeletedItem() {
            //given
            //when
            transaction.begin();
            Item savedItem = em.find(Item.class, this.item.getId());
            assertThat(savedItem).isNotNull();
            em.remove(savedItem);
            transaction.commit();
            //then
            OrderItem savedOrderItem = em.find(OrderItem.class, this.orderItem.getId());
            assertThat(savedOrderItem).isNull();
        }

        @Test
        @DisplayName("주문상품을 제거해도 주문은 남아있다.")
        void leaveOrderByDeletedOrderItem() {
            //given
            //when
            transaction.begin();
            OrderItem savedOrderItem = em.find(OrderItem.class, this.orderItem.getId());
            em.remove(savedOrderItem);
            transaction.commit();
            //then
            Order savedOrder = em.find(Order.class, this.order.getUuid());
            assertThat(savedOrder).isNotNull();
        }

        @Test
        @DisplayName("주문상품을 제거해도 상품은 남아있다.")
        void leaveItemByDeletedOrderItem() {
            //given
            //when
            transaction.begin();
            OrderItem savedOrderItem = em.find(OrderItem.class, this.orderItem.getId());
            em.remove(savedOrderItem);
            transaction.commit();
            //then
            Item savedItem = em.find(Item.class, this.item.getId());
            assertThat(savedItem).isNotNull();
        }
    }

    private void deleteItemForAfterEach(Item Item) {
        Item savedItem = em.find(Item.class,Item.getId());
        if (savedItem != null)
            em.remove(savedItem);
    }

    private void deleteOrderItemForAfterEach(OrderItem orderItem) {
        OrderItem savedOrderItem = em.find(OrderItem.class,orderItem.getId());
        if (savedOrderItem != null)
            em.remove(savedOrderItem);
    }

    private void deleteMemberForAfterEach(Member member) {
        Member savedMember = em.find(Member.class,member.getId());
        if (savedMember != null)
            em.remove(savedMember);
    }

    private void deleteOrderForAfterEach(Order order) {
        Order savedOrder = em.find(Order.class, order.getUuid());
        if (savedOrder != null)
            em.remove(savedOrder);
    }
}
