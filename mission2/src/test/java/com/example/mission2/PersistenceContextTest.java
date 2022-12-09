package com.example.mission2;

import com.example.mission2.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Slf4j
public class PersistenceContextTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("1차 캐시 동일성 테스트")
    void persistenceContextTest() {
        // 비영속 객체
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer();
        newCustomer.setUuid(uuid);
        newCustomer.setName("영지");
        newCustomer.setAddress("안양시 영지네");
        newCustomer.setEmail("youngji804@naver.com");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // 영속화
        em.persist(newCustomer);

        // 1차 캐시 조회
        var customer1 = em.find(Customer.class, uuid);
        var customer2 = em.find(Customer.class, uuid);

        // 영속 엔티티의 동일성 보장
        assertThat(newCustomer == customer1).isTrue();
        assertThat(customer1 == customer2).isTrue();

        // customer 준영속
        em.clear();

        // 쿼리가 전송되기전에 영속성 컨텍스트 clear하여 DB에서 찾을 수 없음
        var customer3 = em.find(Customer.class, uuid);
        assertThat(customer3).isNull();

        // 다시 영속화
        em.merge(newCustomer);
        var customer4 = em.find(Customer.class, uuid);
        assertThat(customer4).isNotNull();

        transaction.commit();


        // db 조회
        transaction.begin();
        var customer5 = em.find(Customer.class, uuid);
        transaction.commit();
        assertThat(customer5).isNotNull();

        // 트랜잯션이 끝날때 1차 캐시도 사라진다고 알고 있는데 오ㅐ 1차 캐시에서 가져올까요..???
        assertThat(customer4 == customer5).isFalse();

    }

    @Test
    @DisplayName("remove는 commit 전에 이뤄져도 쿼리가 날라다.")
    void removeTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer();
        newCustomer.setUuid(uuid);
        newCustomer.setName("영지");
        newCustomer.setAddress("안양시 영지네");
        newCustomer.setEmail("youngji804@naver.com");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 아무 쿼리도 날라가지 않음
        transaction.begin();

        em.persist(newCustomer);
        em.clear();

        transaction.commit();

        // insert ,delete 쿼리 모두 날라감.
        transaction.begin();

        em.persist(newCustomer);
        em.remove(newCustomer);

        transaction.commit();
    }

    @Test
    @DisplayName("update 감지 테스트 - 트랜잭션이 끝난다고 준영속화가 되는 것이 아님")
    void updateTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer();
        newCustomer.setUuid(uuid);
        newCustomer.setName("영지");
        newCustomer.setAddress("안양시 영지네");
        newCustomer.setEmail("youngji804@naver.com");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


        transaction.begin();
        em.persist(newCustomer); // 영속화
        newCustomer.setAge(28);
        transaction.commit();

        em.detach(newCustomer); // 준영속

        transaction.begin();
        newCustomer.setAge(29); // 적용 안됨
        transaction.commit();

        transaction.begin();
        em.merge(newCustomer); // 영속화
        newCustomer.setName("새이름"); // 적용됨
        transaction.commit();
    }

    @Test
    void insertFailTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer();
        newCustomer.setUuid(uuid);
        newCustomer.setName("영지");
        newCustomer.setAddress("안양시 영지네");
        newCustomer.setEmail("youngji804@naver.com");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 한번의 insert 쿼리 발생 -> 영속
        transaction.begin();
        em.persist(newCustomer);
        em.persist(newCustomer);
        transaction.commit();

        // newCustoer가 영속화 되어있어 쿼리가 날라가지 않음??
        transaction.begin();
        em.persist(newCustomer);
        transaction.commit();

        try {
            transaction.begin();
            em.clear();
            em.persist(newCustomer); // 중복 key 로 insert error
            transaction.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

}
