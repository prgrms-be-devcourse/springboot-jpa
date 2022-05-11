package com.study.springbootJpa;

import static com.study.springbootJpa.domain.OrderStatus.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;

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

	@Test
	@DisplayName("member와 order의 양방향 연관관계 테스트")
	void member_order() {
		var entityManager = entityManagerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();

		Member member = new Member();
		member.setName("hyebpark");
		member.setNickName("hyeb");
		member.setAddress("부산시 몰랑몰랑");
		member.setAge(12);

		entityManager.persist(member);
		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderStatus(OPENED);
		order.setOrderDateTime(LocalDateTime.now());
		order.setMemo("문 앞에 두고 가주세요~");
		member.addOrder(order);

		entityManager.persist(order);
		transaction.commit();

		entityManager.clear();
		entityManager.find(Order.class, order.getUuid());

	}

	@Test
	@DisplayName("orderItem과 Item 단방향 연관관계")
	void orderItem_item() {
		var entityManager = entityManagerFactory.createEntityManager();
		var transaction = entityManager.getTransaction();

		transaction.begin();

		Member member = new Member();
		member.setName("hyebpark");
		member.setNickName("hyeb");
		member.setAddress("부산시 몰랑몰랑");
		member.setAge(12);

		entityManager.persist(member);
		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		order.setOrderStatus(OPENED);
		order.setOrderDateTime(LocalDateTime.now());
		order.setMemo("문 앞에 두고 가주세요~");
		member.addOrder(order);

		entityManager.persist(order);

		log.info("member에서 가져온 order의 메모 정보: {}", member.getOrders().get(0).getMemo());
		log.info("order에서 가져온 member의 name : {}", order.getMember().getNickName());
		log.info("order에서 가져온 member의 주문 수 : {}", order.getMember().getOrders().size());

		Item item = new Item();
		item.setPrice(1000);
		item.setStockQuantity(10);

		entityManager.persist(item);

		OrderItem orderItem = new OrderItem();
		orderItem.setPrice(2000);
		orderItem.setItem(item);
		orderItem.setOrder(order);
		orderItem.setOrderStatus(OPENED);

		entityManager.persist(orderItem);

		entityManager.clear();
		entityManager.find(Order.class, order.getUuid());

		log.info("orderItem 에서 가져온 item의 가격 : {}", orderItem.getItem().getPrice());
		log.info("orderItem 에서 가져온 item의 재고 수량 : {}", orderItem.getItem().getStockQuantity());
	}
}
