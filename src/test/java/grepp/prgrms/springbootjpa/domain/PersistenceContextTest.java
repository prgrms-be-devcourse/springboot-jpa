package grepp.prgrms.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    void insert(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first");
        customer.changeLastName("last");

        em.persist(customer);
        em.flush();

        transaction.commit();
    }

    @Test
    @Transactional
    void find_DB(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first");
        customer.changeLastName("last");

        em.persist(customer);

        transaction.commit();

        em.detach(customer);

        Customer selected = em.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @Transactional
    void use_cache(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first");
        customer.changeLastName("last");

        em.persist(customer);

        transaction.commit();

        Customer selected = em.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    @Transactional
    void update(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first");
        customer.changeLastName("last");

        em.persist(customer);

        transaction.commit();

        transaction.begin();
        customer.changeFirstName("newfirst");
        customer.changeLastName("newlast");

        transaction.commit();
    }

    @Test
    @Transactional
    void delete(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first");
        customer.changeLastName("last");
        em.persist(customer);

        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();
    }

    @Test
    @Transactional
    void 쓰기_지연(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();


        log.info("persist customer1");
        Customer customer = new Customer();
        customer.changeId(1L);
        customer.changeFirstName("first1");
        customer.changeLastName("last1");
        em.persist(customer);

        log.info("persist customer2");
        Customer customer2 = new Customer();
        customer2.changeId(2L);
        customer2.changeFirstName("first2");
        customer2.changeLastName("last2");
        em.persist(customer2);

        log.info("persist customer3");
        Customer customer3 = new Customer();
        customer3.changeId(3L);
        customer3.changeFirstName("first2");
        customer3.changeLastName("last2");
        em.persist(customer3);

        transaction.commit();

        log.info("after commit");
    }
}
