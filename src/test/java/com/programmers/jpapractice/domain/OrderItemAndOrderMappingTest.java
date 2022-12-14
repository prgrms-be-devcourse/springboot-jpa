package com.programmers.jpapractice.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpapractice.domain.order.Order;
import com.programmers.jpapractice.domain.order.OrderItem;
import com.programmers.jpapractice.domain.order.OrderStatus;
import com.programmers.jpapractice.repository.OrderItemRepository;
import com.programmers.jpapractice.repository.OrderRepository;

@DataJpaTest
public class OrderItemAndOrderMappingTest {

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrderRepository orderRepository;

	@Test
	@DisplayName("연관관계 매핑 test - 상품의 주문상품 조회 성공")
	void findOrderItemInOrderTest() {
		// given
		Order order = new Order(LocalDateTime.now(), OrderStatus.OPENED, "부재 시 연락주세요.");
		OrderItem orderItem = new OrderItem(2000000, 2);
		order.addOrderItem(orderItem);
		orderRepository.save(order);

		// when
		Order findOrder = orderRepository.findById(order.getId()).get();

		// then
		assertThat(findOrder.getOrderItems().contains(orderItem)).isTrue();
	}

	@Test
	@DisplayName("연관관계 매핑 test - 주문상품의 주문 조회 성공")
	void findOrderInOrderItemTest() {
		// given
		Order order = new Order(LocalDateTime.now(), OrderStatus.OPENED, "부재 시 연락주세요.");
		OrderItem orderItem = new OrderItem(2000000, 2);
		orderItem.setOrder(order);
		orderItemRepository.save(orderItem);

		// when
		OrderItem findOrderItem = orderItemRepository.findById(orderItem.getId()).get();

		// then
		assertThat(findOrderItem.getOrder()).isEqualTo(order);
	}
}
