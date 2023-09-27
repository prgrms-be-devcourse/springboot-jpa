package com.kdt.lecturejpa.domain.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdt.lecturejpa.domain.item.Item;
import com.kdt.lecturejpa.domain.item.ItemRepository;
import com.kdt.lecturejpa.domain.member.Member;
import com.kdt.lecturejpa.domain.member.MemberRepository;
import com.kdt.lecturejpa.domain.order_item.OrderItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public Order createOrder(Long itemId, Long memberId, int orderItemQuantity) {
		Item item = itemRepository
			.findById(itemId)
			.orElseThrow(
				() -> new RuntimeException("존재하지 않는 Item 입니다.")
			);

		Member member = memberRepository
			.findById(memberId)
			.orElseThrow(
				() -> new RuntimeException("존재하지 않는 member 입니다.")
			);

		OrderItem orderItem = OrderItem.createOrderItem(item, orderItemQuantity);

		Order order = Order.createOrder(orderItem, member, "", OrderStatus.OPENED);
		orderItem.attachOrder(order);
		orderRepository.save(order);

		return order;
	}
}
