package prgms.jpamission2.persistenceTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.jpamission2.config.domain.Customer;
import prgms.jpamission2.config.domain.CustomerRepository;

@SpringBootTest
public class PersistenceContextTest {
    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp(){
        repository.deleteAll();
    }

    @Test
    void save(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("oh");
        customer.setLastName("sehan");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.find(Customer.class,1L);
    }

    @Test
    void read() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("oh");
        customer.setLastName("sehan");

        em.persist(customer);
        transaction.commit();

        em.clear();

        Customer entity = em.find(Customer.class, 2L);
        em.find(Customer.class, 2L);
    }

    @Test
    void update(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("oh");
        customer.setLastName("sehan");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = entityManager.find(Customer.class,1L);
        customer.setFirstName("ph");
        customer.setLastName("sehan");

        transaction.commit();

    }

    @Test
    void delete(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("oh");
        customer.setLastName("sehan");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = entityManager.find(Customer.class,1L);
        entityManager.remove(entity);

        transaction.commit();
    }

}
