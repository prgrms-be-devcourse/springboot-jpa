package org.prgms.springbootjpa.mission3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission3.domain.member.Member;
import org.prgms.springbootjpa.mission3.domain.member.MemberRepository;
import org.prgms.springbootjpa.mission3.domain.order.Order;
import org.prgms.springbootjpa.mission3.domain.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgms.springbootjpa.mission3.domain.order.OrderStatus.CREATED;

@SpringBootTest
class OrderMemberRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    Member member = new Member("현지", "딩딩", 25, "서울시 마포구");
    Order order = new Order(CREATED, "집 앞 배송", member);

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 멤버_생성() {
        Member saveMember = memberRepository.save(member);
        orderRepository.save(order);

        Optional<Member> optionalFindMember = memberRepository.findById(saveMember.getId());

        assertThat(optionalFindMember.isPresent(), is(true));

        Member findMember = optionalFindMember.get();

        assertThat(findMember.getId(), is(saveMember.getId()));
    }

    @Test
    void 주문_생성() {
        Member saveMember = memberRepository.save(member);
        Order saveOrder = orderRepository.save(order);

        member.addOrder(saveOrder);

        Optional<Order> optionalFindOrder = orderRepository.findById(saveOrder.getId());

        assertThat(optionalFindOrder.isPresent(), is(true));

        Order findOrder = optionalFindOrder.get();

        assertThat(findOrder.getId(), is(saveOrder.getId()));
        assertThat(saveOrder.getMember().getId(), is(saveMember.getId()));
        assertThat(saveMember.getOrders().size(), is(1));
    }
}
