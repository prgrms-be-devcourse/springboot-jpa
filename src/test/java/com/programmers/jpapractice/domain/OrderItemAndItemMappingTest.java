package com.programmers.jpapractice.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpapractice.domain.order.Item;
import com.programmers.jpapractice.domain.order.OrderItem;
import com.programmers.jpapractice.repository.ItemRepository;
import com.programmers.jpapractice.repository.OrderItemRepository;

@DataJpaTest
public class OrderItemAndItemMappingTest {

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	ItemRepository itemRepository;

	@AfterEach
	void tearDown() {
		orderItemRepository.deleteAll();
		itemRepository.deleteAll();
	}

	@Test
	@DisplayName("연관관계 매핑 test - 상품의 주문상품 조회 성공")
	void findOrderItemInItemTest() {
		// given
		Item item = new Item("노트북", 1000000, 100);
		OrderItem orderItem = new OrderItem(2000000, 2);
		item.addOrderItem(orderItem);
		itemRepository.save(item);

		// when
		Item findItem = itemRepository.findById(item.getId()).get();

		// then
		assertThat(findItem.getOrderItems().contains(orderItem)).isTrue();
	}

	@Test
	@DisplayName("연관관계 매핑 test - 주문상품의 상품 조회 성공")
	void findItemInOrderItemTest() {
		// given
		Item item = new Item("노트북", 1000000, 100);
		OrderItem orderItem = new OrderItem(2000000, 2);
		orderItem.setItem(item);
		orderItemRepository.save(orderItem);

		// when
		OrderItem findOrderItem = orderItemRepository.findById(orderItem.getId()).get();

		// then
		assertThat(findOrderItem.getItem()).isEqualTo(item);
	}
}
