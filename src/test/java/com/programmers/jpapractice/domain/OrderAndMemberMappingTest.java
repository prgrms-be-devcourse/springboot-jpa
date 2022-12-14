package com.programmers.jpapractice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpapractice.domain.order.Member;
import com.programmers.jpapractice.domain.order.Order;
import com.programmers.jpapractice.domain.order.OrderStatus;
import com.programmers.jpapractice.repository.MemberRepository;
import com.programmers.jpapractice.repository.OrderRepository;

@DataJpaTest
public class OrderAndMemberMappingTest {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("연관관계 매핑 test - 회원의 주문 조회 성공")
	void findOrderInMemberTest() {
		// given
		Member member = new Member("권성준", "Jerome", 26, "서울시", "백엔드 개발자 지망생입니다.");
		Order order = new Order(LocalDateTime.now(), OrderStatus.OPENED, "부재 시 연락주세요.");
		member.addOrder(order);
		memberRepository.save(member);

		// when
		Member findMember = memberRepository.findById(member.getId()).get();

		// then
		assertThat(findMember.getOrders().contains(order)).isTrue();
	}

	@Test
	@DisplayName("연관관계 매핑 test - 주문의 회원 조회 성공")
	void findMemberInOrderTest() {
		// given
		Member member = new Member("권성준", "Jerome", 26, "서울시", "백엔드 개발자 지망생입니다.");
		Order order = new Order(LocalDateTime.now(), OrderStatus.OPENED, "부재 시 연락주세요.");
		order.setMember(member);
		memberRepository.save(member);
		orderRepository.save(order);

		// when
		Order findOrder = orderRepository.findById(order.getId()).get();

		// then
		assertThat(findOrder.getMember()).isEqualTo(member);
	}
}
