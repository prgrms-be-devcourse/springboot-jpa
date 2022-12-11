package com.programmers.kwonjoosung.springbootjpa.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    private EntityManagerFactory emf;

    private Customer generateTestCustomer() {
        return new Customer("joosung", "kwon");
    }

    @Test
    @DisplayName("영속화 및 영속 상태 테스트")
    void managed_TEST() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer("joosung", "kwon"); // 비영속 상태

        em.persist(customer); // 영속화 -> 영속성 컨텍스트에서 관리

        assertThat(em.contains(customer)).isTrue(); // 영속성 컨텍스트에 포함되어 있는지 확인

        Customer savedCustomer = em.find(Customer.class, customer.getId());// 1차 캐시에서 조회
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
        em.flush(); // DB에 저장
        em.clear(); // 영속성 컨텍스트 초기화

        Customer detachedCustomer = em.find(Customer.class, customer.getId()); // DB에서 조회
        assertThat(detachedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(detachedCustomer.getLastName()).isEqualTo(customer.getLastName());
        transaction.commit();
    }

    @Test
    @DisplayName("준영속 상태(detached 된 상태)에서 persist()를 하면 예외가 발생한다.")
    void managed_TEST2() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        em.flush(); // DB에 저장하지 않아더라도 detached 된 상태에서 persist를 하면 예외가 발생한다.
        em.detach(customer);
        // DB에는 있고 영속성 컨텍스트에는 없는 상태에서 persist하면 어떻게 되는가? -> detached entity passed to persist 에러 발생
        assertThatThrownBy(() -> em.persist(customer)).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("DB에 있는 객체를 수정하고 persist()하기 -> 준영속 상태에서 persist()를 하면 예외가 발생한다. -> merge()를 사용해야 한다.")
    void managed_TEST3() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        em.flush();
        em.detach(customer);

        // 준영속 상태에서 수정하기
        customer.changeLastName("kim");
        customer.changeFirstName("sungjoo");
        // 수정 했더라도 detached 된 상태에서 persist를 하면 예외가 발생한다. -> 아마 내부적으로 Entity의 상태를 확인해서 detached 상태면 예외를 발생시키는 것 같다.
        assertThatThrownBy(() -> em.persist(customer)).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("영속화된 객체는 더티 체킹을 통해 변경사항이 DB에 반영된다.")
    void managed_TEST4() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        // em.detach(customer); 만약 detach를 하면 더티 체킹이 되지 않는다.
        customer.changeFirstName("SUNGJOO");
        customer.changeLastName("KIM");

        Customer savedCustomer = em.find(Customer.class, customer.getId()); // 1차 캐시에서 조회
        assertThat(savedCustomer.getFirstName()).isEqualTo("SUNGJOO");
        assertThat(savedCustomer.getLastName()).isEqualTo("KIM");

        em.flush(); // -> DB에는 이때 반영됨

        savedCustomer = em.find(Customer.class, customer.getId()); // 1차 캐시에서 조회
        assertThat(savedCustomer.getFirstName()).isEqualTo("SUNGJOO");
        assertThat(savedCustomer.getLastName()).isEqualTo("KIM");

        transaction.commit();
        /* SQL
        * INSERT와 UPDATE만 실행된다.
        * -> SELECT는 실행 x
        * 트랜잭션이 커밋 되었더라도 영속성 컨텍스트는 종료되지 않았기 때문에 1차 캐시에서 조회가 가능한 것으로 추정
        * */

    }

    @Test
    @DisplayName("flush() 하기 전에 준영속 상태이면 DB에 반영되지 않는다.")
    void detached_TEST1() {
        /* 반복되는 작업을 줄이려면
         *  EntityManager @Autowired로 injection 받은 후
         * @BeforeEach에서 em.joinTransaction()을 해주면 된다 -> 현서님 코드 참조
         * */
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);

        assertThat(em.contains(customer)).isTrue();

        em.detach(customer); // 준영속 상태 -> 영속성 컨텍스트에서 분리

        assertThat(em.contains(customer)).isFalse();

        Customer savedCustomer = em.find(Customer.class, customer.getId());// DB에서 조회X -> Why? flush가 일어나지 않았기 때문에 DB에 저장되지 않음
        assertThat(savedCustomer).isNull();
        transaction.commit();
        /* SQL
        * SELECT 쿼리만 나감
        * 이것으로 알 수 있는 사실은 트랜잭션 커밋 or flush 전에 detach가 일어나면 영속성 컨텍스트에서 분리되어 DB에 저장되지 않는다(쿼리도 안날아감)는 것을 알 수 있음
        * */
    }

    @Test
    @DisplayName("flush() 한 후에는 준영속 상태이더라도 find()를 통해 DB에서 찾을 수 있다.")
    void detached_TEST2() {
        /* 반복되는 작업을 줄이려면
         *  EntityManager @Autowired로 injection 받은 후
         * @BeforeEach에서 em.joinTransaction()을 해주면 된다 -> 현서님 코드 참조
         * */
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        assertThat(em.contains(customer)).isTrue();

        em.flush(); // 영속성 컨텍스트의 변경 내용을 DB에 반영

        em.detach(customer);

        assertThat(em.contains(customer)).isFalse();  // 준영속 상태이기 때문에 false

        Customer savedCustomer = em.find(Customer.class, customer.getId());// 하지만 DB에서 조회가 가능
        assertThat(savedCustomer).isNotNull();
        transaction.commit();
        /* SQL
         * INSERT 쿼리 후에 SELECT 쿼리가 나감
         * FIND는 1차 캐시(영속성 컨텍스트)에 없으면 DB에서 조회하기 때문에 는 것이기 때문에 SLECT 쿼리가 나감 -> 따라서 NULL이 아님
         * */
    }

    @Test
    @DisplayName("clear()는 모든 객체를 준영속 상태로 만든다.")
    void detached_TEST3() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();
        Customer customer2 = new Customer("sungjoo", "kim");

        em.persist(customer);
        em.persist(customer2);

        assertThat(em.contains(customer)).isTrue();
        assertThat(em.contains(customer2)).isTrue();

        em.clear(); // 영속성 컨텍스트 초기화

        assertThat(em.contains(customer)).isFalse();
        assertThat(em.contains(customer2)).isFalse();

        Customer savedCustomer = em.find(Customer.class, customer.getId());
        Customer savedCustomer2 = em.find(Customer.class, customer2.getId());
        assertThat(savedCustomer).isNull();
        assertThat(savedCustomer2).isNull();
        transaction.commit();
    }

    @Test
    @DisplayName("close()는 EntityManager를 종료한다.")
    void close_TEST() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();
        em.persist(customer);

        transaction.commit();

        em.close(); // EntityManager 종료
        /* EntityManager가 종료되었기 때문에 사용 불가 -> Exception 발생(EntityManager is closed) */
        // Customer savedCustomer = em.find(Customer.class, customer.getId());
        em = emf.createEntityManager();
        Customer savedCustomer = em.find(Customer.class, customer.getId()); // 새로운 EntityManager를 생성하여 사용 가능
        assertThat(savedCustomer).isNotNull();
    }

    @Test
    @DisplayName("merge를 통해 준영속 상태의 객체를 다시 영속성 컨텍스트에 포함시킬수 있다.")
    void merge_TEST1() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();
        em.persist(customer);
        assertThat(em.contains(customer)).isTrue();
        em.flush();
        em.detach(customer);
        assertThat(em.contains(customer)).isFalse();

        Customer mergedCustomer = em.merge(customer);
        assertThat(em.contains(customer)).isFalse(); // 새로운 객체를 다시 반환하기 때문에 이전 객체는 false(영속성 컨텍스트에 반영되지 않은 상태)
        assertThat(em.contains(mergedCustomer)).isTrue();
        transaction.commit();
    }

    @Test
    @DisplayName("flush() 되지 않은 채로 준영속 상태의 객체를 merge할 수 없다.")
    void merge_TEST2() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();
        em.persist(customer);
        em.detach(customer);
        em.merge(customer); // flush 되지 않은 채로 detach 된 객체를 merge할 수 없다. -> WHY?
        assertThatThrownBy(transaction::commit).isInstanceOf(RollbackException.class);
    }

    @Test
    @DisplayName("한번 DB에 저장이 된 객체는 merge.")
    void merge_TEST3() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();
        em.persist(customer);
        assertThat(em.contains(customer)).isTrue();
        em.flush();
        em.detach(customer);
        assertThat(em.contains(customer)).isFalse();

        Customer mergedCustomer = em.merge(customer);
        assertThat(em.contains(customer)).isFalse(); // 새로운 객체를 다시 반환하기 때문에 이전 객체는 false(영속성 컨텍스트에 반영되지 않은 상태)
        assertThat(em.contains(mergedCustomer)).isTrue();
        transaction.commit();
    }

    @Test
    @DisplayName("준영속상태에서 수정하고 merge하기.")
    void merge_TEST4() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        em.flush();
        em.detach(customer);

        // 준영속 상태에서 수정하기
        customer.changeLastName("kim");
        customer.changeFirstName("sungjoo");

        // detached 된 객체는 merge를 통해 영속성 컨텍스트에 포함시킬 수 있다.
        em.merge(customer);
        transaction.commit();

        Customer updatedCustomer = em.find(Customer.class, customer.getId());
        assertThat(updatedCustomer.getFirstName()).isEqualTo("sungjoo");
        assertThat(updatedCustomer.getLastName()).isEqualTo("kim");

    }

    @Test
    @DisplayName("영속화 되지 않은 객체 merge하기")
    void merge_TEST5() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        Customer mergedCustomer = em.merge(customer); // 영속화 되지 않은 객체를 merge하면 insert 쿼리가 실행된다.
        transaction.commit();

        Customer savedCustomer = em.find(Customer.class, mergedCustomer.getId()); // 1차 캐시에서 조회됨
        assertThat(savedCustomer.getFirstName()).isEqualTo("joosung");
        assertThat(savedCustomer.getLastName()).isEqualTo("kwon");
    }

    @Test
    @DisplayName("삭제 상태 - remove()는 영속성 컨텍스트와 DB에서 해당 객체를 제거한다.")
    void delete_TEST1() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        em.flush();
        em.remove(customer); // 삭제 상태

        assertThat(em.contains(customer)).isFalse();
        assertThat(em.find(Customer.class, customer.getId())).isNull();
        transaction.commit();
    }

    @Test
    @DisplayName("remove 된 객체는 find를 통해 조회할 수 없다.")
    void delete_TEST2() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = generateTestCustomer();

        em.persist(customer);
        em.flush();
        em.remove(customer);
        assertThat(em.contains(customer)).isFalse();
        Customer customer1 = em.find(Customer.class, customer.getId());
        assertThat(customer1).isNull();
        transaction.commit();
        /* SQL
        * INSERT 후에 DELETE 쿼리가 나감
        * 중간에 SELECT 쿼리가 나가지 않음 -> remove를 하면 내부적으로 deleted 상태로 변경되고 (find하면 null 반환), flush가 일어나면 DB에서 삭제됨
        * */
    }

}
