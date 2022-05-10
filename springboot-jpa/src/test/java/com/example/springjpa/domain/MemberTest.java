package com.example.springjpa.domain;

import com.example.springjpa.domain.order.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
class MemberTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member();
        member.setName("kang");
        member.setAddress("경기도 용인시");
        member.setAge(10);
        member.setNickName("닉네임");
        member.setDescription("mountain");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(member);
        transaction.commit();
    }

}

// create table member (
// id bigint not null,
// address varchar(255) not null,
// age integer not null,
// description varchar(255),
// name varchar(30) not null,
// nick_name varchar(30) not null,
// primary key (id)
// )

