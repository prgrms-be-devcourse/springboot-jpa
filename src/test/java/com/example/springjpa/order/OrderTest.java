package com.example.springjpa.order;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.assertj.core.api.Assertions;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderTest {

	@Autowired
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	private Item chair;
	private Item book;
	private Member member;

	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
		tx = em.getTransaction();

		tx.begin();

		chair = new Furniture(1000, 100, 11, 11);
		book = new Book(500, 50, "이상한 나라의 앨리스");
		member = createMember();

		em.persist(chair);
		em.persist(book);
		em.persist(member);

		tx.commit();
		em.clear();
	}

	@AfterEach
	void tearDown() {
		tx.begin();

		em.createQuery("DELETE FROM OrderItem ").executeUpdate();
		em.createQuery("DELETE FROM Order ").executeUpdate();
		em.createQuery("DELETE FROM Member").executeUpdate();
		em.createQuery("DELETE FROM Item").executeUpdate();

		tx.commit();
	}

	@Test
	@DisplayName("주문을 저장하면 orderItem 도 저장된다")
	public void test_order() {
		tx.begin();

		Order order = createOrder();

		em.persist(order);

		tx.commit();
	}

	@Test
	@DisplayName("주문 정보를 불러올 때 Member 는 초기화되지 않은 프록시 객체다")
	public void test_proxy() {
		tx.begin();

		Order order = createOrder();

		em.persist(order);
		tx.commit();
		em.clear();

		tx.begin();

		Order foundOrder = em.find(Order.class, order.getUuid());
		Member proxyMember = foundOrder.getMember();

		assertThat(emf.getPersistenceUnitUtil().isLoaded(proxyMember)).isFalse();

		tx.commit();
	}

	@Test
	@DisplayName("주문 정보를 불러온 후 멤버에 접근한 이후에는 멤버는 초기화된 프록시 객체다")
	public void test_not_proxy() {
		tx.begin();

		Order order = createOrder();

		em.persist(order);
		tx.commit();
		em.clear();

		tx.begin();

		Order foundOrder = em.find(Order.class, order.getUuid());

		Member proxyMember = foundOrder.getMember();

		String initializedProxyMemberName = proxyMember.getName(); // 초기화 발생

		assertThat(emf.getPersistenceUnitUtil().isLoaded(proxyMember)).isTrue();

		tx.commit();

	}

	@Test
	@DisplayName("다대일 단방향 관계일 경우 일 의 삭제 요청 시 fk 제약조건을 위반하여 커밋과정에서 예외가 발생한다")
	public void remove() {
		tx.begin();

		Order order = createOrder();

		em.persist(order);
		tx.commit();

		tx.begin();

		Item foundChair = em.find(Item.class, chair.getId());
		em.remove(foundChair);

		Assertions.assertThatThrownBy(() -> tx.commit())
			.getCause().getCause()
			.hasCauseInstanceOf(JdbcSQLIntegrityConstraintViolationException.class);
	}

	@Test
	@DisplayName("일대 다에서 일 측의 CascadeType 에 REMOVE 가 포함되어있으며 orphanRemoval 이 true 면 일의 삭제 요청 시 다 측을 모두 삭제한 후 일을 삭제한다")
	public void remove_member_test() {
		tx.begin();

		Order order = createOrder();

		em.persist(order);
		tx.commit();

		tx.begin();

		Order foundOrder = em.find(Order.class, order.getUuid());
		em.remove(foundOrder);

		tx.commit();
	}

	private Member createMember() {
		return new Member("kimLee", "kirin", 33, "Triple street");
	}

	private Order createOrder() {
		Order order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED);

		OrderItem orderItem1 = new OrderItem(10, chair, 1000);
		OrderItem orderItem2 = new OrderItem(4, book, 500);

		order.orderedByMember(member);
		order.addOrderItem(orderItem1);
		order.addOrderItem(orderItem2);

		return order;
	}

}
