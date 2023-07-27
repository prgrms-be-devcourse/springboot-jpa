package org.prgms.springbootjpa.mission2.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission1.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository repository;

    EntityManager entityManager;

    EntityTransaction transaction;


    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        repository.deleteAll();
    }

    Customer customer = new Customer(1L, "hyeonji", "park");

    @Test
    void 저장() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();
    }

    @Test
    void 조회_1차캐시_이용() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        Customer entity = entityManager.find(Customer.class, customer.getId());
        assertThat(entity, samePropertyValuesAs(customer));
    }


    @Test
    void 조회_DB_이용() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        entityManager.detach(customer);

        Customer entity = entityManager.find(Customer.class, customer.getId());
        assertThat(entity, samePropertyValuesAs(customer));
    }

    @Test
    void 수정_dirtyCheck_적용() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();

        customer.changeFirstName("hyeonz");

        transaction.commit();

        Customer entity = entityManager.find(Customer.class, customer.getId());
        assertThat(entity.getFirstName(), is("hyeonz"));
    }

    @Test
    void 수정_dirtyCheck_적용_안됨() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        entityManager.detach(customer);

        transaction.begin();

        customer.changeFirstName("hyeonz");

        transaction.commit();

        Customer afterEntity = entityManager.find(Customer.class, customer.getId());
        assertThat(afterEntity.getFirstName(), not("hyeonz"));
    }

    @Test
    void 삭제() {
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();

        Customer beforeCommit = entityManager.find(Customer.class, customer.getId());
        assertThat(beforeCommit, samePropertyValuesAs(customer));

        entityManager.remove(customer);

        transaction.commit();

        Customer afterCommit = entityManager.find(Customer.class, customer.getId());
        assertThat(afterCommit, nullValue());
    }
}
