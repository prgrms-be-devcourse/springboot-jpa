package com.example.springjpa.repository;

import com.example.springjpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    private final Customer customer = new Customer("g", "test");
    private final Customer customer2 = new Customer("g", "test2");
    private final Customer customer3 = new Customer("g", "test3");

    @Autowired
    public CustomerRepository repository;

    @BeforeAll
    void setup(){
        repository.saveAll(List.of(customer, customer2, customer3));
        customer.setId(1L);
        customer2.setId(2L);
        customer3.setId(3L);
    }

    @AfterAll
    void deleteAll(){
        repository.deleteAll();
    }

    @Test
    void 조회() {
        val customers = repository.findAll();

        assertThat(customers).containsExactlyInAnyOrder(customer, customer2, customer3);
    }

    @Test
    void firstName으로_조회() {
        val customers = repository.findByFirstName("g");

        assertThat(customers).containsExactlyInAnyOrder(customer, customer2, customer3);
    }

    @Test
    void lastName으로_조회() {
        val customers = repository.findByLastName("test");

        assertThat(customers).containsExactly(customer);
    }

    @Test
    void firstName과_lastName으로_조회() {
        val customer = repository.findByFirstNameAndLastName("g", "test");

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get().getFirstName()).isEqualTo("g");
        assertThat(customer.get().getLastName()).isEqualTo("test");
    }

    @Test
    void 저장() {
        val newCustomer = new Customer("gil", "geuno");
        val savedCustomer = repository.save(newCustomer);

        val byId = repository.findById(savedCustomer.getId());

        assertThat(byId.isPresent()).isTrue();
        Customer retCustomer = byId.get();
        assertThat(retCustomer.getFirstName()).isEqualTo("gil");
        assertThat(retCustomer.getLastName()).isEqualTo("geuno");
        repository.delete(retCustomer);
    }

    @Test
    @Transactional
    void 수정() {
        val testCustomer = repository.findByLastName("test").get(0);
        testCustomer.setFirstName("gil");
        testCustomer.setLastName("geuno");

        // 1차 캐쉬에서 가져옴
        val maybeCustomer = repository.findById(testCustomer.getId());

        assertThat(maybeCustomer.isPresent()).isTrue();
        assertThat(maybeCustomer.get()).usingRecursiveComparison().isEqualTo(testCustomer);
    }
}