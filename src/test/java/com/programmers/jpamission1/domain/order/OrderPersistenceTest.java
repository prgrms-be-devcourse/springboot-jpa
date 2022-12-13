package com.programmers.jpamission1.domain.order;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpamission1.repository.MemberRepository;
import com.programmers.jpamission1.repository.OrderRepository;

@DataJpaTest

public class OrderPersistenceTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	OrderRepository orderRepository;

	Member createMember() {
		Member member = new Member("geonwoo", "rose", 25, "Korea");
		member.updateDescription("i'm lee");
		member.updateCreatedAt(LocalDateTime.now().withNano(0));
		member.updateCreatedBy("geonwoo");
		return member;
	}

	Order createOrder(Member member) {
		Order order = new Order(LocalDateTime.now().withNano(0), OrderStatus.OPENED, "부재시 전화주세요", member);
		order.updateCreatedAt(LocalDateTime.now().withNano(0));
		order.updateCreatedBy("geonwoo");
		return order;
	}

	@Test
	@DisplayName("양방향 관계저장")
	void mappingTest() {
		//given
		Member member = createMember();
		Order order = createOrder(member);

		memberRepository.save(member);
		orderRepository.save(order);

		//when
		Optional<Order> maybeOrder = orderRepository.findById(order.getUuid());
		Assertions.assertThat(maybeOrder).isPresent();
		Order savedOrder = maybeOrder.get();
		Member relatedMember = savedOrder.getMember();

		//then
		Assertions.assertThat(relatedMember.getAge()).isEqualTo(member.getAge());
		Assertions.assertThat(relatedMember.getAddress()).isEqualTo(member.getAddress());
		Assertions.assertThat(relatedMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(relatedMember.getNickName()).isEqualTo(member.getNickName());
		Assertions.assertThat(relatedMember.getDescription()).isEqualTo(member.getDescription());
		Assertions.assertThat(relatedMember.getCreatedAt()).isEqualTo(member.getCreatedAt());
		Assertions.assertThat(relatedMember.getCreatedBy()).isEqualTo(member.getCreatedBy());

	}

	@Test
	@DisplayName("양방향 연관관계 CasCade 삭제")
	void casCadeTest() {

		//given
		Member member = createMember();
		Order order = createOrder(member);

		memberRepository.save(member);
		orderRepository.save(order);

		//when
		memberRepository.delete(member);

		Optional<Order> maybeOrder = orderRepository.findById(order.getUuid());

		//then
		Assertions.assertThat(maybeOrder).isEmpty();

	}

	@Test
	@DisplayName("양방향 연관관계 orphanRemoval 삭제")
	void orphanTest() {

		//given
		Member member = createMember();
		Order order = createOrder(member);

		memberRepository.save(member);
		orderRepository.save(order);

		//when
		member.removeOrder(order);

		memberRepository.flush();
		orderRepository.flush();

		//then
		Optional<Order> maybeOrphanOrder = orderRepository.findById(order.getUuid());
		Assertions.assertThat(maybeOrphanOrder).isEmpty();

	}

	@Test
	@DisplayName("객체 그래프 탐색")
	void entityGraph() {
		//given
		Member member = createMember();
		Order order = createOrder(member);

		memberRepository.save(member);
		orderRepository.save(order);

		//when
		Member relateMember = order.getMember();

		//then
		Assertions.assertThat(member).usingRecursiveComparison().isEqualTo(relateMember);

	}
}
