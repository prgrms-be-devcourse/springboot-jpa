package prgrms.assignment.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgrms.assignment.jpa.domain.Customer;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersistenceContextLifecycleTest {

    @Autowired
    EntityManagerFactory emf;

    Customer customer = new Customer("seonghyeon", "kim");

    @Test
    @DisplayName("비영속 상태의 Customer 를 영속 상태로 만든다.")
    void testSave() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();
    }


    @Test
    @DisplayName("Customer 를 준영속 상태로 만들고 조회한다. 영속 상태가 아니기 때문에 동일성을 보장해주지 않고, select 쿼리가 나간다.")
    void testSearch() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.detach(customer);

        var retrievedCustomer = em.find(Customer.class, customer.getId());

        assertThat(retrievedCustomer).isNotEqualTo(customer);
        assertThat(retrievedCustomer.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("Customer 를 1차 캐시를 통해서 조회한다. 영속 상태이기 때문에 동일성을 보장해주고, select 쿼리가 나가지 않는다.")
    void testUseCash() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        var retrievedCustomer = em.find(Customer.class, customer.getId());

        assertThat(retrievedCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("변경감지를 통해 Customer 를 업데이트한다.")
    void testUpdate() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.updateName("updated-seonghyeon", "updated-kim");
        transaction.commit();
        System.out.println(transaction.isActive());
    }

    @Test
    @DisplayName("Customer 를 영속성 컨텍스트에서 삭제시킨다.")
    void testDelete() {
        var em = emf.createEntityManager();
        var transaction = em.getTransaction();

        transaction.begin();
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();
    }
}
