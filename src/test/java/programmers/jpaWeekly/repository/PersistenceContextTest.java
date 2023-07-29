package programmers.jpaWeekly.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import programmers.jpaWeekly.customer.entity.Customer;
import programmers.jpaWeekly.customer.repository.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("customer 저장 테스트")
    void saveTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("kim", "yeseul");
        entityManager.persist(customer);

        transaction.commit();

        Customer foundCustomer = entityManager.find(Customer.class, 1L);
        assertThat(foundCustomer.getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("id로 customer 조회")
    void findByIdTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("Lee", "yeseul");
        entityManager.persist(customer);

        transaction.commit();

        Customer foundCustomer = entityManager.find(Customer.class, 1L);

        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }


    @Test
    @DisplayName("customer 수정 테스트")
    void updateTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("kim", "yeseul");
        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();
        customer.updateCustomerName("Jo", "HeeJo");

        transaction.commit();

        Customer foundCustomer = entityManager.find(Customer.class, 1L);

        assertThat(foundCustomer.getLastName()).isEqualTo("Jo");
        assertThat(foundCustomer.getFirstName()).isEqualTo("HeeJo");
    }

    @Test
    @DisplayName("customer 삭제 테스트")
    void deleteTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = new Customer("kim", "yeseul");
        entityManager.persist(customer);

        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);

        transaction.commit();
    }
}
