package devcoursejpa.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void saveTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit(); // entityManager.flush();
    }

    @Test
    @DisplayName("DB로부터 조회")
    void readDB() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer); // 영속 -> 준영속

        Customer findCustomer = entityManager.find(Customer.class, 1L);
        log.info("{} {}", findCustomer.getFirstName(), findCustomer.getLastName());
    }

    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("joomin");
        customer.setLastName("cha");

        transaction.commit();
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }
}