package org.devcourse.assignment.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.devcourse.assignment.domain.customer.Address;
import org.devcourse.assignment.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class EntityLifeCycleTest {
    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private CustomerRepository customerRepository;

    EntityManager em;

    EntityTransaction transaction;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @DisplayName("비영속 엔티티를 영속화한다.")
    @Test
    void newToManaged() {
        // given - 비영속 엔티티
        Customer customer = createCustomer("의진", "jinny");

        // when - 영속화
        em.persist(customer);

        // then
        assertTrue(em.contains(customer));
    }

    @DisplayName("[참고] 트랜잭션이 없다면, DB에 변경사항이 반영되지 않는다.")
    @Test
    void newToManagedWithoutTransaction() {
        // given - 비영속 엔티티
        Customer customer = createCustomer("의진", "jinny");

        // when - 영속화
        em.persist(customer);

        // then
        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertThatThrownBy(() -> found.get());
    }

    @DisplayName("[참고] 트랜잭션이 있다면, DB에 변경사항이 반영된다.")
    @Test
    void newToManagedWithTransaction() {
        // ==== 트랜잭션 시작 ====
        transaction.begin();

        // given - 비영속 엔티티
        Customer customer = createCustomer("의진", "jinny");

        // when - 영속화
        em.persist(customer);

        // ==== 트랜잭션 종료 (auto flush) ====
        transaction.commit();

        // then
        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertDoesNotThrow(() -> found.get());
    }

    @DisplayName("DB에서 가져온 엔티티는 영속 상태이다.")
    @Test
    void find() {
        // given - 영속 엔티티를 DB에 flush
        transaction.begin();

        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        transaction.commit();

        // when - DB에서 엔티티를 조회
        Customer found = em.find(Customer.class, customer.getId());

        // then
        assertTrue(em.contains(customer));
        assertThat(found).isEqualTo(customer); // 영속성 컨텍스트는 엔티티의 동일성 보장
    }

    @DisplayName("영속 엔티티를 준영속화한다.")
    @Test
    void toDetached() {
        // given - 영속 엔티티
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        // when - 준영속화
        em.detach(customer);

        // then
        assertFalse(em.contains(customer));
    }

    @DisplayName("영속성 컨텍스트를 비울 경우, 모든 영속 엔티티를 준영속화한다.")
    @Test
    void clear() {
        // given - 영속 엔티티
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        // when - 영속성 컨텍스트 clear
        em.clear();

        // then
        assertFalse(em.contains(customer));
    }

    @DisplayName("영속성 컨텍스트를 닫을 경우, 모든 영속 엔티티를 준영속화한다.")
    @Test
    void close() {
        // given
        transaction.begin();

        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        transaction.commit();

        // when - 영속성 컨텍스트 close
        em.close();

        // then - customer는 준영속 상태이므로 변경 감지가 동작 X
        Address address = createAddress("서울", "동일로 179길", "123123");
        customer.saveAddress(address);

        EntityManager newEntityManager = emf.createEntityManager();
        Customer found = newEntityManager.find(Customer.class, customer.getId());
        assertThat(found.getAddress()).isNull();
    }

    @DisplayName("준영속 엔티티를 다시 영속화한다.")
    @Test
    void detachedToManaged() {
        // given - 준영속 엔티티
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);
        em.detach(customer);

        assertFalse(em.contains(customer));

        // when - 영속화
        Customer merged = em.merge(customer); // merge()를 하면, 준영속 엔티티의 복사본을 영속 상태로 변환하고 그 복사본을 반환

        // then
        assertTrue(em.contains(merged));
    }

    @DisplayName("영속 엔티티를 삭제한다.")
    @Test
    void managedToRemove() {
        // given - 영속 엔티티
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        // when - 삭제
        em.remove(customer);

        // then
        assertFalse(em.contains(customer));
    }

    @DisplayName("삭제된 엔티티를 다시 영속화한다.")
    @Test
    void removedToManaged() {
        // given - 삭제된 엔티티
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);
        em.remove(customer);

        // when - 영속화
        em.persist(customer);

        // then
        assertTrue(em.contains(customer));
    }

    @DisplayName("영속 엔티티를 DB에 저장할 수 있다.")
    @Test
    void flushManaged() {
        // given - 영속 엔티티
        transaction.begin();

        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);

        // DB에 반영
        transaction.commit(); // commit이 되면 em.flush() 수행

        // then
        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertDoesNotThrow(() -> found.get());
    }

    @DisplayName("삭제된 엔티티를 DB에 반영할 수 있다.")
    @Test
    void flushRemoved() {
        // given - 삭제된 엔티티
        transaction.begin();
        Customer customer = createCustomer("의진", "jinny");
        em.persist(customer);
        em.remove(customer);

        // when - DB에 반영
        transaction.commit(); // commit이 되면 em.flush() 수행

        // then
        List<Customer> all = customerRepository.findAll();
        assertThat(all).isEmpty();
    }

    private static Customer createCustomer(String name, String nickName) {
        return Customer.builder()
                .name(name)
                .nickName(nickName)
                .description("안녕하세요:)")
                .build();
    }

    private static Address createAddress(String city, String street, String zipCode) {
        return Address.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode)
                .build();
    }
}
