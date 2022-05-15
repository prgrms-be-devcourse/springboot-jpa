package com.prgms.springbootjpa.lifecycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgms.springbootjpa.domain.Customer;
import com.prgms.springbootjpa.domain.Name;
import com.prgms.springbootjpa.repository.CustomerRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(com.prgms.springbootjpa.config.DataSourceConfig.class)
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @AfterEach
    void cleanUp() {
        em.close();
        repository.deleteAll();
    }

    @Test
    @DisplayName("영속화 테스트")
    void testPersist() {
        transaction.begin();

        var customer = new Customer(1L, new Name("eunhyuk", "bahng")); //비영속 상태
        em.persist(customer); //영속화

        assertThat(em.contains(customer)).isTrue();

        log.info("before transaction.commit()");
        transaction.commit(); //트랜잭션이 커밋되는 순간 flush() -> DB와 동기화를 위해 쿼리 수행.
        log.info("after transaction.commit()");
    }

    @Test
    @DisplayName("1차 캐시를 이용한 조회")
    void testUsingCacheInPersistenceContext() {
        transaction.begin();

        var customer = new Customer(1L, new Name("eunhyuk", "bahng")); //비영속 상태
        em.persist(customer); //영속화

        assertThat(em.contains(customer)).isTrue();

        transaction.commit();

        log.info("before em.find()");
        var findEntity = em.find(Customer.class, customer.getId()); //SQL 수행 X
        log.info("after em.find()");

        assertAll(
            () -> assertThat(findEntity).isNotNull(),
            () -> assertThat(findEntity.getId()).isEqualTo(customer.getId()),
            () -> assertThat(findEntity.getName()).isEqualTo(customer.getName())
        );
    }

    @Test
    @DisplayName("DB를 이용한 조회")
    void testUsingDBWhenFindEntity() {
        transaction.begin();

        var customer = new Customer(1L, new Name("eunhyuk", "bahng")); //비영속 상태
        em.persist(customer);

        assertThat(em.contains(customer)).isTrue();

        transaction.commit();

        em.clear(); //영속성 컨텍스트 초기화

        log.info("before em.find()");
        var findEntity = em.find(Customer.class, customer.getId()); //SQL 수행
        log.info("after em.find();");

        assertAll(
            () -> assertThat(findEntity).isNotNull(),
            () -> assertThat(findEntity.getId()).isEqualTo(customer.getId()),
            () -> assertThat(findEntity.getName()).isEqualTo(customer.getName()),
            () -> assertThat(em.contains(findEntity)).isTrue()
        );
    }

    @Test
    @DisplayName("변경 감지")
    void testDirtyChecking() {
        transaction.begin();

        var customer = new Customer(1L, new Name("eunhyuk", "bahng")); //비영속 상태
        em.persist(customer);

        assertThat(em.contains(customer)).isTrue();

        transaction.commit();

        transaction.begin();

        var findEntity = em.find(Customer.class, 1L);
        findEntity.changeName(new Name("honggu", "kang"));

        log.info("before commit()");
        transaction.commit(); //update SQL 수행!
        log.info("after commit()");
    }

    @Test
    @DisplayName("삭제")
    void testRemove() {
        transaction.begin();

        var customer = new Customer(1L, new Name("eunhyuk", "bahng")); //비영속 상태
        em.persist(customer);

        assertThat(em.contains(customer)).isTrue();

        transaction.commit();

        transaction.begin();

        var findEntity = em.find(Customer.class, customer.getId());
        em.remove(findEntity);

        assertThat(em.contains(findEntity)).isFalse();

        log.info("before commit()");
        transaction.commit(); //Delete SQL 수행!
        log.info("after commit()");
    }
}
