package com.study.springbootJpa;

import static com.study.springbootJpa.domain.OrderStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.springbootJpa.domain.Item;
import com.study.springbootJpa.domain.Member;
import com.study.springbootJpa.domain.Order;
import com.study.springbootJpa.domain.OrderItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OrderMappingTest {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	private Long memberId;
	private String orderId;

	@BeforeEach
	void setUp() {
		var entityManager = entityManagerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();
		transaction.begin();

		Member member = new Member();
		member.setName("hyebpark");
		member.setNickName("hyeb");
		member.setAddress("부산시 몰랑몰랑");
		member.setAge(12);

		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderStatus(OPENED);
		order.setOrderDateTime(LocalDateTime.now());
		order.setMemo("문 앞에 두고 가주세요~");

		member.addOrder(order);

		entityManager.persist(member);
		entityManager.persist(order);

		memberId = member.getId();
		orderId = order.getUuid();

		transaction.commit();
	}

	@Test
	@DisplayName("member와 order의 양방향 연관관계 테스트")
	void member_order() {
		var entityManager = entityManagerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();

		var member = entityManager.find(Member.class, memberId);
		var order = entityManager.find(Order.class, orderId);

		log.info("member id : {} ", memberId);
		log.info("found member id : {} ", member.getId());
		assertThat(member.getOrders().get(0).getUuid()).isEqualTo(orderId);

		log.info("order id : {} ", orderId);
		log.info("found order id : {} ", order.getUuid());
		assertThat(order.getMember().getId()).isEqualTo(memberId);

		transaction.commit();
	}

	@Test
	@DisplayName("order와 orderItem의 양방향 연관관계, orderItem과 Item 단방향 연관관계")
	void orderItem_item() {
		var entityManager = entityManagerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();
		var order = entityManager.find(Order.class, orderId);

		transaction.begin();

		Item item = new Item();
		item.setPrice(1000);
		item.setStockQuantity(10);

		OrderItem orderItem = new OrderItem();
		orderItem.setPrice(2000);
		orderItem.setItem(item);
		orderItem.setOrder(order);
		orderItem.setOrderStatus(OPENED);

		entityManager.persist(item);
		entityManager.persist(orderItem);
		transaction.commit();

		var itemId = item.getId();
		var orderItemId = orderItem.getId();
		var foundOrderItem = entityManager.find(OrderItem.class, orderItemId);

		// order와 orderItem의 양방향 관계
		assertThat(order.getOrderItems().get(0).getId()).isEqualTo(orderItemId);
		assertThat(foundOrderItem.getOrder().getUuid()).isEqualTo(orderId);
		//orderItem과 Item의 단방향 관계
		assertThat(foundOrderItem.getItem().getId()).isEqualTo(itemId);
	}
}
