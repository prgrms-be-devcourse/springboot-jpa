package com.kdt.lecture.domain.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kdt.lecture.domain.member.Member;
import com.kdt.lecture.domain.member.MemberRepository;
import com.kdt.lecture.domain.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("order 추가시 order 테이블에도 멤버가 추가된다 ")
    @Test
    void limitNameLength() {
        Member member = new Member();
        member.setName("예원");
        member.setNickName("예오닝");
        member.setAddress("경기도 용인시");

        Order order = new Order();

        member.addOrder(order);

        assertTrue(member.getOrders().contains(order));
        assertEquals(order.getMember(), member);
    }

}