package org.prgrms.springjpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    EntityManager em;

    EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @Test
    void 엔티티를_영속화한후_저장할수있다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park"); //비영속 상태
        //when
        em.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); //em.flush();
        //then
        assertThat(customer.getId()).isEqualTo(1L);
    }

    @Test
    void 엔티티를_준영속상태로_만들면_DB를_통해_조회한다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park"); //비영속 상태
        //when
        em.persist(customer);
        transaction.commit();
        em.detach(customer); // 영속 -> 준영속
        Customer selected = em.find(Customer.class, 1L); //DB를 통해 조회
        //then
        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 엔티티를_저장후_조회하면_1차캐시에서_우선_조회한다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park");
        //when
        em.persist(customer);
        transaction.commit();
        Customer selected = em.find(Customer.class, 1L); //1차 캐시를 통해 조회
        //then
        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 엔티티를_저장후_트랜잭션_커밋을_하지않으면_1차캐시에만_저장된다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park"); //비영속 상태
        //when
        em.persist(customer);
        Customer primaryCacheCustomer = em.find(Customer.class, 1L); //1차 캐시를 통해 조회
        em.clear();
        Customer databaseCustomer = em.find(Customer.class, 1L); //DB를 통해 조회
        //then
        assertThat(primaryCacheCustomer).isEqualTo(customer);
        assertThat(databaseCustomer).isNull();
    }

    @Test
    void 스냅샷과_비교해_엔티티에_변경이_일어나면_변경감지가_일어난다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park");
        em.persist(customer);
        transaction.commit();
        //when
        transaction.begin();
        String updatedName = "gildong";
        customer.setFirstName(updatedName); // 트랜잭션 내에서 엔티티 변경이 일어나면 변경 감지
        Customer update = em.find(Customer.class, 1L);
        transaction.commit(); //커밋 시점에 쓰기지연 저장소 -> DB로 쿼리 전송
        //then
        assertThat(update.getFirstName()).isEqualTo(updatedName);
    }

    @Test
    void 엔티티를_영속성_컨텍스트에서_삭제할수있다() {
        //given
        transaction.begin();
        Customer customer = new Customer("woojin", "park"); //비영속 상태
        em.persist(customer);
        transaction.commit();
        //when
        transaction.begin();
        em.remove(customer);
        transaction.commit();
        Customer findCustomer = em.find(Customer.class, 1L);
        boolean containCustomerOrNot = em.contains(findCustomer);
        //then
        assertThat(containCustomerOrNot).isFalse();
    }
}
