package org.prgrms.studyjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    @Order(1)
    void saveCustomer() {
        Customer customer = new Customer(1L, "Ari", "Aster");
        repository.save(customer);

        Customer findCustomer = repository.findById(customer.getId()).get();
        assertThat(customer, is(findCustomer));
    }

    @Test
    @DisplayName("고객을 id로 조회할 수 있다.")
    @Order(2)
    void findCustomer() {
        Customer findCustomer = repository.findById(1L).get();
        assertThat(findCustomer.getId(), is(1L));
        assertThat(findCustomer.getFirstName(), is("Ari"));
        assertThat(findCustomer.getLastName(), is("Aster"));
    }

    @Test
    @DisplayName("고객의 이름을 변경할 수 있다.")
    @Order(3)
    void updateCustomerName() {
        Customer customer = repository.findById(1L).get();

        customer.changeFirstName("Jiyeon");
        customer.changeLastName("Choi");
        repository.save(customer);

        Customer updatedCustomer = repository.findById(1L).get();
        assertThat(updatedCustomer.getFirstName(), is("Jiyeon"));
        assertThat(updatedCustomer.getLastName(), is("Choi"));
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    @Order(4)
    void deleteCustomer() {
        Customer customer = repository.findById(1L).get();
        repository.delete(customer);
        Optional<Customer> findCustomer = repository.findById(1L);
        assertThat(findCustomer.isEmpty(), is(true));
    }
}
