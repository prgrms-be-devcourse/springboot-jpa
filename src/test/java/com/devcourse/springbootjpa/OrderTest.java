package com.devcourse.springbootjpa;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devcourse.springbootjpa.domain.order.Item;
import com.devcourse.springbootjpa.domain.order.Member;
import com.devcourse.springbootjpa.domain.order.Order;
import com.devcourse.springbootjpa.domain.order.OrderItem;
import com.devcourse.springbootjpa.domain.order.OrderStatus;
import com.devcourse.springbootjpa.domain.repository.ItemRepository;
import com.devcourse.springbootjpa.domain.repository.MemberRepository;
import com.devcourse.springbootjpa.domain.repository.OrderItemRepository;
import com.devcourse.springbootjpa.domain.repository.OrderRepository;

@DataJpaTest
class OrderTest {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	ItemRepository itemRepository;
	Member member;
	Item item;
	OrderItem orderItem;
	Order order;

	@BeforeEach
	void setup() {
		member = Member.builder()
				.name("seungwon")
				.nickname("baekdung")
				.age(26)
				.address("Gangnam, Seoul")
				.description("vip")
				.build();
		memberRepository.save(member);

		item = Item.builder()
				.price(999_000)
				.stockQuantity(100)
				.build();
		itemRepository.save(item);
		orderItem = OrderItem.builder()
				.price(999_000)
				.quantity(10)
				.item(item)
				.build();
		orderItemRepository.save(orderItem);
		order = Order.builder()
				.uuid(UUID.randomUUID().toString())
				.memo("부재시 문앞에 놔주세요")
				.orderStatus(OrderStatus.OPENED)
				.member(member)
				.orderItems(List.of(orderItem))
				.build();
	}

	@Test
	@DisplayName("주문 생성이 가능하다")
	void CreateTest() {
		// given
		// when
		Order saved = orderRepository.save(order);
		// then
		assertThat(saved.getOrderItems().get(0), is(equalTo(orderItem)));
		assertThat(saved.getMember(), is(equalTo(member)));
	}

	@Test
	@DisplayName("주문 아이디로 검색이 가능하다")
	void SearchTest() {
		// given
		Order saved = orderRepository.save(order);
		String id = saved.getUuid();
		// when
		Order found = orderRepository.findById(id).get();
		// then
		assertThat(found, is(equalTo(saved)));
	}

	@Test
	@DisplayName("주문을 취소시 상태가 변경된다")
	void deleteTest() {
		// given
		Order saved = orderRepository.save(order);
		String id = saved.getUuid();
		// when
		Order found = orderRepository.findById(id).get();
		found.changeOrderStatus(OrderStatus.CANCELED);
		// then
		assertThat(found.getOrderStatus(), is(equalTo(OrderStatus.CANCELED)));
	}

}
