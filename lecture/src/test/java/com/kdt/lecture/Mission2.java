package com.kdt.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.lecture.domain.Customer;
import com.kdt.lecture.repository.CustomerRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Mission2 {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void 생명주기_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction(); // entityManger의 트랜잭션을 관리한다고 보면 된다.
        Customer customer = new Customer(1L, "hunki", "kim"); // 비영속
        em.persist(customer); // 영속
        em.detach(customer); // 영속 상태의 customer객체를 영속성컨텍스트에서 분리한다.
        em.clear(); // 영속상태의 모든 객체를 영속성 컨텐스트에서 분리한다.
        em.close(); // 영속성 컨텍스트를 종료한다.
        em.remove(customer); // customer 엔티티를 영속성 컨텍스트에서 분리하고, DB에서도 삭제한다.
    }

    @Test
    void 저장_테스트() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = new Customer(1L, "hunki", "kim");
        //when
        transaction.begin();
        em.persist(customer); // PR Point1 . 영속만 시켰는데 어떻게 알아서 Repository를 찾아서 저장하는 건지 궁금합니다!
        transaction.commit();
        //then
        assertThat(customerRepository.findById(customer.getId())).isNotEmpty();
    }

    @Test
    void 조회_테스트() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = new Customer(1L, "hunki", "kim");
        //when
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        Customer entity = em.find(Customer.class,
            1L); // 1L식별자를 통해 커스토머 클래스를 반환하는 엔티티를 찾는다. 1차캐시에서..
        //then
        assertThat(entity).isEqualTo(customer); // 이제보니 주소값도 같은 듯 하다.
    }

    @Test
    void 수정_테스트() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = new Customer(1L, "hunki", "kim");
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        //when
        transaction.begin();
        customer.setFirstName("HUNKI");
        customer.setLastName("KIM");
        transaction.commit(); // flush -> UPDATED
        //then
        assertThat(customer.getFirstName()).isEqualTo(customerRepository.findById(1L).get().getFirstName()); // 영속화 되어 있기 떄문에 가능
        em.clear(); // 영속 분리
        //다시 같은 행위 반복
        transaction.begin();
        customer.setFirstName("hunki");
        customer.setLastName("kim");
        transaction.commit(); // flush -> UPDATED
        assertThat(customer.getFirstName()).isEqualTo(customerRepository.findById(1L).get().getFirstName());
        //오류가 뜬다. 영속을 분리해서 customer가 캐시에 없기 때문에 일어나는 현상인듯 하다.
    }

    @Test
    void 삭제_테스트() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = new Customer(1L, "hunki", "kim");
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        //when
        transaction.begin();
        em.remove(customer);
        Customer Entity = customerRepository.findById(1L).get(); // 아직 플러쉬가 일어나지 않았기 때문에 남아있다.
        log.info("{}",Entity.getFirstName());
        transaction.commit();
        //then
        assertThat(customerRepository.findAll()).isEmpty(); // 플러쉬 일어났다.
    }
}
