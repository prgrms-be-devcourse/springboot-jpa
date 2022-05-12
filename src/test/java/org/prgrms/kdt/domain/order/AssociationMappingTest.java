package org.prgrms.kdt.domain.order;

import static java.time.LocalDateTime.*;
import static java.util.UUID.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.prgrms.kdt.domain.order.OrderStatus.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssociationMappingTest {

	@Autowired
	EntityManagerFactory emf;

	Member newMember = new Member("dooly", "hoit", 11, "고길동이네", "yangA");
	Order newOrder = new Order(randomUUID().toString(), now(), ACCEPTED, "둘리가 시킴");

	@Test
	@DisplayName("order를 조회 → member도 함께 조회된다.")
	void testSearchMemberFromOrder() {
	    // given
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(newMember);

		// when
		newOrder.changeMember(newMember);
		entityManager.persist(newOrder);

		transaction.commit();
		entityManager.clear();

	    // then
		Order order = entityManager.find(Order.class, newOrder.getUuid());
		assertThat(order, notNullValue());
		assertThat(order.getMember(), notNullValue());
		assertThat(order.getMember().getId(), is(newMember.getId()));
	}
}