package org.prgms.jpa.domain;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.jpa.domain.order.Item;
import org.prgms.jpa.domain.order.Member;
import org.prgms.jpa.domain.order.Order;
import org.prgms.jpa.domain.order.OrderItem;
import org.prgms.jpa.domain.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OrderMappingTest {

	@Autowired
	EntityManagerFactory emf;

	@DisplayName("Order를 저장 할 때 나머지도 같이 저장되어야 한다.")
	@Test
	void order_save_test_with_cascade() {
		final Member member = Member.builder()
			.name("seunghan")
			.nickName("han")
			.address("서울시 영등포구")
			.age(25)
			.build();

		final Item item = Item.builder()
			.name("새콤달콤")
			.price(500)
			.stockQuantity(20)
			.build();

		final OrderItem orderItem = OrderItem.builder()
			.orderStatus(OrderStatus.START)
			.price(3000)
			.build();

		orderItem.setItem(item);

		final Order order = Order.builder()
			.orderStatus(OrderStatus.START)
			.memo("주문 시 집 앞에 놔주세요!")
			.build();

		order.setMember(member);
		order.setOrderItems(orderItem);

		final EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(order); // 영속
		tx.commit();

		log.info("order Id : {}", order.getId());

		em.clear(); // 영속성 컨텍스트 초기화

		final Order findOrder = em.find(Order.class, order.getId()); // select 쿼리가 날라가야 한다.

		// getMember, getOrderItem을 호출하면 프록시 객체가 반환, Lazy 작동하는 것 육안을 확인
		log.info("============= select member Query 날라가요! ==============");
		assertThat(findOrder.getMember().getAge()).isEqualTo(25);

		log.info("============= select Order Item 쿼리 날라기요! ====================");
		assertThat(findOrder.getOrderItems().get(0).getPrice()).isEqualTo(3000);

	}
}
