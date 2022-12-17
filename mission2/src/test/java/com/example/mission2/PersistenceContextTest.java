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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        var newCustomer = new Customer(uuid,"youngji804@naver.com","안양시 영지네","영지",28);

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

        EntityManager em2 = emf.createEntityManager();

        // db 조회
        transaction.begin();
        var customer5 = em2.find(Customer.class, uuid);
        transaction.commit();
        assertThat(customer5).isNotNull();

        assertThat(customer4 == customer5).isFalse();

    }

    @Test
    @DisplayName("remove는 commit 전에 이뤄져도 쿼리가 날라다.")
    void removeTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer(uuid,"youngji804@naver.com","안양시 영지네","영지",28);

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

        var customer = em.find(Customer.class, uuid);
        assertThat(customer).isNull();

    }

    @Test
    @DisplayName("update 감지 테스트 - 트랜잭션이 끝난다고 준영속화가 되는 것이 아님")
    void updateTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer(uuid,"youngji804@naver.com","안양시 영지네","영지");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


        transaction.begin();
        em.persist(newCustomer); // 영속화
        newCustomer.changeAge(28);
        transaction.commit();

        em.detach(newCustomer); // 준영속

        transaction.begin();
        newCustomer.changeAge(29); // 적용 안됨
        transaction.commit();

        var findCustomer = em.find(Customer.class, uuid);
        assertThat(findCustomer).isNotNull();
        assertThat(findCustomer.getAge()).isEqualTo(28);

        transaction.begin();
        newCustomer = em.merge(newCustomer); // 영속화 - 병합 엔티티를 새로 반환
        newCustomer.changeName("새이름"); // 적용됨
        transaction.commit();

        var findCustomer2 = em.find(Customer.class, uuid);
        assertThat(findCustomer2).isNotNull();
        assertThat(findCustomer2.getName()).isEqualTo("새이름");
        assertThat(findCustomer2.getAge()).isEqualTo(29);

    }

    @Test
    @DisplayName("중복 id insert fail 테스트")
    void insertFailTest() {
        var uuid = UUID.randomUUID().toString();
        var newCustomer = new Customer(uuid,"youngji804@naver.com","안양시 영지네","영지",28);

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 한번의 insert 쿼리 발생 -> 영속
        transaction.begin();
        em.persist(newCustomer);
        em.persist(newCustomer);
        transaction.commit();


        transaction.begin();
        em.clear();
        assertThrows(Exception.class, ()->{
            em.persist(newCustomer); // 중복 key 로 insert error
            transaction.commit();
        });


    }

}
