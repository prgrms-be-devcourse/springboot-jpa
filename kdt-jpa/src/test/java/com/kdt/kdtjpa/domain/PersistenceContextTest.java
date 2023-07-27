package com.kdt.kdtjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PersistenceContextTest {

    private static final Long ID = 1L;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction transaction;

    private Customer customer;

    @BeforeEach
    void setUp() {
        this.repository.deleteAll();
        this.em = emf.createEntityManager();
        this.transaction = em.getTransaction();
        this.customer = Customer.createCustomer()
                .id(ID)
                .lastName("Hwang")
                .firstName("Junho")
                .build();
    }

    @DisplayName("persist 상태를 커밋하면 flush가 되면서 DB에 저장이 된다.")
    @Test
    void save() {
        // When
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        Customer foundCustomer = em.find(Customer.class, ID);

        // Then
        assertThat(customer).isEqualTo(foundCustomer);
    }

    @DisplayName("영속상태의 객체를 준영속 상태로 바꾸면 dirty checking이 일어나지 않는다.")
    @Test
    void detach() {
        // Given
        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // When
        em.detach(customer);
        transaction.begin();
        customer.changeLastName("Kim");
        customer.changeFirstName("Happy");
        transaction.commit();
        Customer foundCustomer = em.find(Customer.class, 1L);

        // Then
        assertThat(customer.getFullName()).isNotEqualTo(foundCustomer.getFullName());
    }

    @DisplayName("persist 상태를 커밋하면 flush가 되면서 DB에 저장이 된다.")
    @Test
    void flush() {
        // Given
        transaction.begin();
        em.persist(customer);

        // When
        em.flush();
        Customer foundCustomer = em.find(Customer.class, ID);

        // Then
        assertThat(customer).isEqualTo(foundCustomer);
    }

    @DisplayName("영속 상태의 객체를 변경하고 커밋하면 DB에 뱐걍된 상태의 객체가 저장된다.")
    @Test
    void update() {
        // Given
        transaction.begin();
        em.persist(customer);
        customer.changeFirstName("Kim");
        customer.changeLastName("Happy");
        transaction.commit();

        // When
        Customer updatedCustomer = em.find(Customer.class, ID);

        // Then
        assertThat(updatedCustomer)
                .extracting("lastName", "firstName")
                .containsExactly(new Name("Happy"), new Name("Kim"));
    }

    @DisplayName("삭제한 객체를 조회하면 NPE가 발생한다.")
    @Test
    void remove() {
        //Given
        transaction.begin();
        em.persist(customer);
        transaction.commit();

        // When
        transaction.begin();
        em.remove(customer);
        transaction.commit();

        // Then
        assertThatThrownBy(() -> em.find(Customer.class, ID).getId())
                .isInstanceOf(NullPointerException.class);
    }
}
