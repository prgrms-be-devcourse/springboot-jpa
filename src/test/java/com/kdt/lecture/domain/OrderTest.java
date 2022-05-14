package com.kdt.lecture.domain;

import com.kdt.lecture.domain.item.Book;
import com.kdt.lecture.domain.item.Car;
import com.kdt.lecture.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import static com.kdt.lecture.domain.Order.createOrder;
import static com.kdt.lecture.domain.OrderItem.createOrderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
//@Transactional
class OrderTest {

    @Autowired
    EntityManagerFactory emf;


    EntityManager em;
    EntityTransaction transaction;

    @BeforeEach
    void setUp(){
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @Test
    public void 멤버_생성() throws Exception {

        //given
        transaction.begin();

        Member member = Member.builder()
                .name("name")
                .nickName("nickName")
                .age(10)
                .address("address")
                .build();

        log.info("member -> {}", member.toString());
        em.persist(member);

        //when
        Member savedMember = em.find(Member.class, member.getId());

        //then
        assertThat(savedMember.getId()).isEqualTo(member.getId());
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getNickName()).isEqualTo(member.getNickName());
        assertThat(savedMember.getAddress()).isEqualTo(member.getAddress());
        assertThat(savedMember.getAge()).isEqualTo(member.getAge());
        assertThat(savedMember.getDescription()).isEqualTo(member.getDescription());
        assertThat(savedMember.getCreatedAt()).isEqualTo(member.getCreatedAt());

        em.clear();
        transaction.commit();
    }

    @Test
    public void 중복_닉네임_멤버_생성() throws Exception {

        transaction.begin();
        //given
        Member member = Member.builder()
                .name("name")
                .nickName("nickName")
                .age(10)
                .address("address")
                .build();

        log.info("member -> {}", member.toString());
        em.persist(member);

        //when
        Member member2 = Member.builder()
                .name("name2")
                .nickName("nickName")
                .age(12)
                .address("address2")
                .build();
        em.persist(member2);

        //then
        assertThrows(RollbackException.class, () -> transaction.commit());
    }

    @Test
    public void 멤버생성_아이템등록_주문생성_연관관계확인() throws Exception {

        transaction.begin();
        //given
        Member member = Member.builder()
                .name("name")
                .nickName("nickName")
                .age(10)
                .address("address")
                .build();
        em.persist(member);

        Book book = new Book("book1", 20000, 9999, "famous writer");
        em.persist(book);

        Car car = new Car("car1", 2000000, 50, 45);
        em.persist(car);

        OrderItem orderBook = createOrderItem(book, 12);
        em.persist(orderBook);

        OrderItem orderCar = createOrderItem(car, 1);
        em.persist(orderCar);

        Order order = createOrder(member, "memo", orderBook, orderCar);
        em.persist(order);

        //then
        assertThat(book.getStockQuantity()).isEqualTo(9999 - 12);
        assertThat(car.getStockQuantity()).isEqualTo(50 - 1);
        assertThat(orderBook.getTotalPrice()).isEqualTo(20000 * 12);
        assertThat(orderCar.getTotalPrice()).isEqualTo(2000000 * 1);
        assertThat(order.getTotalPrice()).isEqualTo(20000 * 12 + 2000000);
        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems()).contains(orderBook);
        assertThat(order.getOrderItems()).contains(orderCar);

        transaction.commit();
    }

    @Test
    public void 멤버생성_아이템등록_주문생성_및_주문취소() throws Exception {

        transaction.begin();
        //given
        Member member = Member.builder()
                .name("name")
                .nickName("nickName")
                .age(10)
                .address("address")
                .build();
        em.persist(member);

        Book book = new Book("book1", 20000, 9999, "famous writer");
        em.persist(book);

        Car car = new Car("car1", 2000000, 50, 45);
        em.persist(car);

        OrderItem orderBook = createOrderItem(book, 12);
        em.persist(orderBook);

        OrderItem orderCar = createOrderItem(car, 1);
        em.persist(orderCar);

        Order order = createOrder(member, "memo", orderBook, orderCar);
        em.persist(order);

        order.cancel();

        //then
        assertThat(book.getStockQuantity()).isEqualTo(9999);
        assertThat(car.getStockQuantity()).isEqualTo(50);assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getOrderItems()).contains(orderBook);
        assertThat(order.getOrderItems()).contains(orderCar);
        transaction.commit();
    }

    @Test
    public void 멤버생성_아이템등록_주문생성_및_주문삭제() throws Exception {

        transaction.begin();
        //given
        Member member = Member.builder()
                .name("name")
                .nickName("nickName")
                .age(10)
                .address("address")
                .build();
        em.persist(member);

        Book book = new Book("book1", 20000, 9999, "famous writer");
        em.persist(book);

        Car car = new Car("car1", 2000000, 50, 45);
        em.persist(car);

        OrderItem orderBook = createOrderItem(book, 12);
        em.persist(orderBook);

        OrderItem orderCar = createOrderItem(car, 1);
        em.persist(orderCar);

        Order order = createOrder(member, "memo", orderBook, orderCar);
        em.persist(order);

        order.cancel();
        em.remove(order);

        transaction.commit();
        //then
        assertThat(em.find(Order.class, order.getUuid())).isNull();
        assertThat(em.find(OrderItem.class, orderCar.getId())).isNull();
        assertThat(em.find(OrderItem.class, orderBook.getId())).isNull();
        assertThat(em.find(Member.class, member.getId())).isNotNull();
        assertThat(em.find(Item.class, car.getId())).isNotNull();
        assertThat(em.find(Item.class, book.getId())).isNotNull();
    }
}