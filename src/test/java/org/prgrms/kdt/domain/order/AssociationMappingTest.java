package org.prgrms.kdt.domain.order;

import static java.time.LocalDateTime.*;
import static java.util.UUID.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.prgrms.kdt.domain.order.OrderStatus.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class AssociationMappingTest {

	@Autowired
	EntityManagerFactory emf;

	Member newMember = new Member("dooly", "hoit", 11, "고길동이네", "yangA");
	Order newOrder = new Order(randomUUID().toString(), now(), ACCEPTED, "둘리가 시킴");
	OrderItem newOrderItem = new OrderItem(1000, 5);
	Item newItem = new Item(1000, 5);

	@Test
	@DisplayName("order를 조회 → member도 함께 조회된다.")
	void testSearchMemberFromOrder() {
	    // given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(newMember);

		newOrder.changeMember(newMember);
		entityManager.persist(newOrder);

		transaction.commit();
		entityManager.clear();

		// when
		Order order = entityManager.find(Order.class, newOrder.getUuid());

	    // then
		assertThat(order, notNullValue());
		assertThat(order.getMember(), notNullValue());
		assertThat(order.getMember().getId(), is(newMember.getId()));
	}

	@Test
	@DisplayName("주문을 저장한다.")
	void testInsertOrder() {
	    // given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

	    // when
		entityManager.persist(newOrder);

		entityManager.persist(newItem);

		newOrderItem.changeOrder(newOrder);
		newOrderItem.changeItem(newItem);
		entityManager.persist(newOrderItem);

		transaction.commit();

	    // then
		Order order = entityManager.find(Order.class, newOrder.getUuid());
		log.info("order: {}", order);
		assertThat(order, notNullValue());
		assertThat(order, samePropertyValuesAs(newOrder));

		assertThat(order.getOrderItems(), notNullValue());
		assertThat(order.getOrderItems().size(), is(1));

		OrderItem orderItem = order.getOrderItems().get(0);
		assertThat(orderItem, samePropertyValuesAs(newOrderItem));

		Item item = orderItem.getItem();
		assertThat(item, notNullValue());
		assertThat(item, samePropertyValuesAs(newItem));
	}

	@Test
	@DisplayName("orderItem을 조회 → order가 <fetch: lazy> 조회된다.")
	void testSearchOrderFromOrderItem() {
		// given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(newOrder);

		newOrderItem.changeOrder(newOrder);
		entityManager.persist(newOrderItem);

		transaction.commit();
		entityManager.clear();

	    // when
		OrderItem orderItem = entityManager.find(OrderItem.class, newOrderItem.getId());

		// then
		// Lazy 로딩 되어 프록시 객체이다.
		// id(PK) 값만 가지고 있고, 그 외의 정보 접근 할때 select query가 실행된다.
		Order order = orderItem.getOrder();
		assertThat(order, instanceOf(HibernateProxy.class));

		log.info("//== before execute select order query ==//");
		assertThat(order.getMemo(), is(newOrder.getMemo()));	// 실제로 사용할때 조회된다.
	}
}