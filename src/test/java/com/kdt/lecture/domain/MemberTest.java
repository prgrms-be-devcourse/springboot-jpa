package com.kdt.lecture.domain;

import com.kdt.lecture.domain.item.Book;
import com.kdt.lecture.domain.item.Car;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import java.util.ArrayList;
import java.util.List;

import static com.kdt.lecture.domain.Order.createOrder;
import static com.kdt.lecture.domain.OrderItem.createOrderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
//@Transactional
class MemberTest {

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
                .orders(new ArrayList<>())
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
                .orders(new ArrayList<>())
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
                .orders(new ArrayList<>())
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

        transaction.commit();
    }

}