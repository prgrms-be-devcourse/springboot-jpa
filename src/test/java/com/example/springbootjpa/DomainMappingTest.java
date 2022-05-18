package com.example.springbootjpa;

import com.example.springbootjpa.domain.items.Book;
import com.example.springbootjpa.domain.items.Fruit;
import com.example.springbootjpa.domain.items.Item;
import com.example.springbootjpa.domain.main.Member;
import com.example.springbootjpa.domain.main.Order;
import com.example.springbootjpa.domain.main.OrderItem;
import com.example.springbootjpa.domain.main.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DomainMappingTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private String memberId;
    private String bookId;
    private String fruitId;

    //기존 DB에 한 명의 Member 객체와 두 개의 Item 객체가 저장되어 있다고 가정한다.
    @BeforeEach
    void setup() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        memberId = UUID.randomUUID().toString();
        Member testMember = Member.builder()
                .uuid(memberId)
                .email("test@gmail.com")
                .description("This is a test member object.")
                .build();

        entityManager.persist(testMember);

        bookId = UUID.randomUUID().toString();
        Item testBook = Book.builder()
                .uuid(bookId)
                .productName("testBook1")
                .price(12000)
                .quantityInStock(150)
                .author("kim")
                .build();

        fruitId = UUID.randomUUID().toString();
        Item testFruit = Fruit.builder()
                .uuid(fruitId)
                .productName("strawberry")
                .price(5000)
                .quantityInStock(500)
                .origin("Korea")
                .build();

        entityManager.persist(testBook);
        entityManager.persist(testFruit);

        transaction.commit();
        entityManager.clear();
    }

    @Test
    @DisplayName("DB에 저장된 Member, Item 객체와 관계 매핑을 하며 Order, OrderItem 객체를 DB에 저장한다.")
    void testOrderItems() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        // Given
        Order testOrder = Order.builder()
                .orderStatus(OrderStatus.ACCEPTED)
                .memo("This is a test memo.")
                .build();

        Item testBook = entityManager.find(Item.class, bookId);
        OrderItem orderItem1 = OrderItem.builder()
                .item(testBook)
                .quantity(50)
                .detail("testBook is added to orderitem1")
                .build();

        Item testFruit = entityManager.find(Item.class, fruitId);
        OrderItem orderItem2 = OrderItem.builder()
                .item(testFruit)
                .quantity(15)
                .detail("testFruit is added to orderitem2")
                .build();

        // When
        Member testMember = entityManager.find(Member.class, memberId);
        testOrder.setMember(testMember);

        testOrder.addOrderitem(orderItem1);
        testOrder.addOrderitem(orderItem2);

        entityManager.persist(testOrder);

        transaction.commit();
        entityManager.clear();

        // Then
        EntityTransaction anotherTransaction = entityManager.getTransaction();
        anotherTransaction.begin();

        Member memberFromDB = entityManager.find(Member.class, memberId);
        Order orderFromDB = memberFromDB.getOrders().get(0);

        // Order와 Member간의 관계 설정 확인
        assertThat(orderFromDB.getId()).isEqualTo(1L);
        assertThat(orderFromDB.getMember().getUuid()).isEqualTo(memberFromDB.getUuid());

        OrderItem orderItem1FromDB = orderFromDB.getOrderItems().get(0);
        OrderItem orderItem2FromDB = orderFromDB.getOrderItems().get(1);

        // OrderItem과 Order간의 관계 설정 확인
        assertThat(orderItem1FromDB.getOrder().getId()).isEqualTo(1L);
        assertThat(orderItem2FromDB.getOrder().getId()).isEqualTo(1L);
        
        // OrderItem과 Item 간의 관계 설정 확인
        assertThat(orderItem1FromDB.getItem().getUuid()).isEqualTo(bookId);
        assertThat(orderItem2FromDB.getItem().getUuid()).isEqualTo(fruitId);

        anotherTransaction.commit();
        entityManager.close();
    }

}
