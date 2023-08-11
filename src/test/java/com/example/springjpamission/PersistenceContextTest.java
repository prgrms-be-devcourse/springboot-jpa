package com.example.springjpamission;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.springjpamission.customer.domain.Customer;
import com.example.springjpamission.customer.domain.Name;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class PersistenceContextTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("고객의 정보를 저장한 후 영속성 컨텍스트를 초기화한 후 저장소에서 정보를 가져왔을 때 같은 primary key로 저장되었는지 확인한다.")
    void save() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        Name name = new Name("영운", "윤");
        Customer customer = new Customer(name);

        em.persist(customer);

        transaction.commit();
        em.clear();

        //then
        Customer findCustomer = em.find(Customer.class, customer.getId());
        assertThat(findCustomer.getId()).isEqualTo(customer.getId());
        assertThat(findCustomer.getName().getFirstName()).isEqualTo("영운");
        assertThat(findCustomer.getName().getLastName()).isEqualTo("윤");
    }

    @Test
    @DisplayName("고객의 정보를 저장소에 저장한 후 이를 수정할 때 의도한 이름으로 수정되었는지 확인한다.")
    void update() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Name name = new Name("영운", "윤");
        Customer customer = new Customer(name);
        em.persist(customer);

        transaction.commit();

        //when
        transaction.begin();
        Customer findCustomer = em.find(Customer.class, customer.getId());
        Name newName = new Name("별", "김");
        findCustomer.changeName(newName);
        em.persist(findCustomer);

        transaction.commit();

        //then
        em.clear();
        Customer updatedCustomer = em.find(Customer.class, findCustomer.getId());

        assertThat(updatedCustomer.getName()).isEqualTo(newName);
    }

}
