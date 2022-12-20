package kdt.springbootjpa.customer;

import kdt.springbootjpa.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void 저장하기() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        entityManager.persist(customer); //비영속 -> 영속
        transaction.commit(); //entityManager.flush()
    }

    @Test
    void 조회하기_1차캐시() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        entityManager.persist(customer); //비영속 -> 영속
        transaction.commit();

        Customer savedCustomer = entityManager.find(Customer.class, customer.getId());

        assertThat(savedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    void 조회하기_DB() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        entityManager.persist(customer); //비영속 -> 영속
        transaction.commit();

        entityManager.detach(customer); //영속 -> 준영속

        Customer savedCustomer = entityManager.find(Customer.class, customer.getId());
        assertThat(savedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    void 수정하기() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        entityManager.persist(customer); //비영속 -> 영속
        transaction.commit();

        transaction.begin(); //update를 위해 다른 트랜잭션 시작
        customer.changeFirstName("tlsdud");
        customer.changeLastName("fortune");
        transaction.commit();

        Customer savedCustomer = entityManager.find(Customer.class, customer.getId());
        assertThat(savedCustomer)
                .hasFieldOrPropertyWithValue("firstName", "tlsdud")
                .hasFieldOrPropertyWithValue("lastName", "fortune");
    }

    @Test
    void 삭제하기() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        entityManager.persist(customer); //비영속 -> 영속
        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
    }
}
