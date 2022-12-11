package com.example.springjpa.domain;

import com.example.springjpa.domain.common.EntityManagerTest;
import com.example.springjpa.domain.order.vo.Name;
import com.example.springjpa.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PersistenceContextTest extends EntityManagerTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer = new Customer(new Name("TaeSan", "Kang"));

    @Test
    @DisplayName("영속성 컨텍스트만을 사용하여 데이터베이스에 저장할 수 있다.")
    void testSaveWithPersistenceContext() {
        execWithTransaction(() -> entityManager.persist(customer));

        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        assertAll(
                () -> assertThat(findCustomer).isPresent(),
                () -> assertThat(findCustomer.get().getId()).isEqualTo(customer.getId())
        );
    }

    @Test
    @DisplayName("Entity매니저의 find를 통해 객체를 조회할 수 있다.")
    void testEntityManagerFind() {
        execWithTransaction(() -> {
            entityManager.persist(customer);

            Customer findCustomer = entityManager.find(Customer.class, customer.getId());
            assertThat(findCustomer.getId()).isEqualTo(customer.getId());
        });
    }

    @Test
    @DisplayName("commit이전에 Detach되면 저장되지 않는다.")
    void testEntityManagerDetachFind() {
        execWithTransaction(() -> {
            entityManager.persist(customer);
            entityManager.detach(customer); // 영속 -> 준영속
            Customer findCustomer = entityManager.find(Customer.class, customer.getId());
            assertThat(findCustomer).isNull();
        });
    }

    @Test
    @DisplayName("DirtyChecking을 통한 Update가 가능하다.")
    void testDirtyChecking() {
        String changeFirstName = "big";
        String changeLastName = "mountain";

        execWithTransaction(() -> {
            entityManager.persist(customer);
            entityManager.flush();
            customer.changeName(new Name(changeFirstName, changeLastName));
            Customer findCustomer = entityManager.find(Customer.class, customer.getId());
            assertAll(
                    () -> assertThat(findCustomer.getName().getFirstName()).isEqualTo(changeFirstName),
                    () -> assertThat(findCustomer.getName().getLastName()).isEqualTo(changeLastName)
            );
        });
    }

    @Test
    @DisplayName("EntityManager.remove를 수행하면 데이터베이스에서도 삭제 된다.")
    void testDelete() {
        execWithTransaction(() -> {
            entityManager.persist(customer);
            entityManager.flush();
            entityManager.remove(customer);
        });

        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        assertThat(findCustomer).isEmpty();
    }
}
