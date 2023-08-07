package com.kdt.lecturejpa.domain.order;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdt.lecturejpa.domain.item.Item;
import com.kdt.lecturejpa.domain.item.ItemRepository;
import com.kdt.lecturejpa.domain.member.Member;
import com.kdt.lecturejpa.domain.member.MemberRepository;
import com.kdt.lecturejpa.domain.order_item.OrderItem;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {
	Member member;
	Item item;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemRepository itemRepository;

	@BeforeAll
	public void setUp() {
		item = new Item(1000, 100, "삼다수");
		member = Member.builder()
			.address("관악구 서림동")
			.name("byeonggon")
			.nickName("gorani")
			.age(25)
			.description("")
			.build();

		itemRepository.save(item);
		memberRepository.save(member);
	}

	@Test
	public void 주문_테스트() {
		//when
		Order samdasuOrder = orderService.createOrder(item.getId(), member.getId(), 10);

		Member actualMember = samdasuOrder.getMember();
		List<OrderItem> byenggonOrderItems = samdasuOrder.getOrderItems();
		OrderItem byeonggonOrderItem = byenggonOrderItems.get(0);
		Item actualItem = byeonggonOrderItem.getItem();

		item = itemRepository.findById(item.getId())
			.orElseThrow(
				() -> new RuntimeException("존재 하지 않는 멤버")
			);

		// then
		assertThat(actualItem, samePropertyValuesAs(item, "orderItems"));
		assertThat(actualMember, samePropertyValuesAs(member, "orders"));
	}
}