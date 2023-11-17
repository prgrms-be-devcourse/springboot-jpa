package com.devcourse.springbootjpaweekly.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.devcourse.springbootjpaweekly.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
public class PersistenceContextTest {

    static final String CUSTOMER_COUNT = "SELECT COUNT(*) FROM customer";
    static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";

    @Autowired
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;

    @BeforeEach
    void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void cleanup() {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.createNativeQuery(DELETE_FROM_CUSTOMER)
                .executeUpdate();

        transaction.commit();

        entityManager.close();
    }

    @DisplayName("New State 엔티티는 영속화되지 않는다.")
    @Test
    void testNewState() {
        // given
        transaction = entityManager.getTransaction();

        // when
        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("name")
                .lastName("last")
                .email("email@email.com")
                .build();

        transaction.commit();

        ThrowingCallable findCustomer = () -> entityManager.find(Customer.class, customer.getId());

        // then
        assertThatIllegalArgumentException().isThrownBy(findCustomer);
    }

    @DisplayName("객체가 영속화 되면 변경사항이 DB에 반영된다.")
    @Test
    void testManagedState() {
        // given
        transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("name")
                .lastName("last")
                .email("email@email.com")
                .build();

        entityManager.persist(customer);

        transaction.commit();

        //when
        transaction.begin();

        customer.updateLastName("kim");

        transaction.commit();

        //then
        entityManager.clear();

        Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

        assertThat(foundCustomer).hasNoNullFieldsOrProperties();

        String actual = foundCustomer.getLastName();
        String expected = customer.getLastName();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Removed State 엔티티는 flush 후 컨텍스트와 DB 삭제된다.")
    @Test
    void testRemovedState() {
        // given
        transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("name")
                .lastName("last")
                .email("email@email.com")
                .build();

        transaction.commit();

        // when
        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();

        // then
        ThrowingCallable findCustomer = () -> entityManager.find(Customer.class, customer.getId());

        assertThatIllegalArgumentException().isThrownBy(findCustomer)
                .withMessage("id to load is required for loading");

        int customerCount = getCustomerCount();

        assertThat(customerCount).isEqualTo(0);
    }

    private int getCustomerCount() {
        transaction.begin();

        int count = entityManager.createNativeQuery(CUSTOMER_COUNT)
                .getFirstResult();

        transaction.commit();

        return count;
    }
}
