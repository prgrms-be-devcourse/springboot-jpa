package com.kdt.jpa.customer.repository;

import static org.assertj.core.api.Assertions.*;

import com.kdt.jpa.customer.domain.Customer;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 저장 성공")
    void saveTest() {
        //Given
        Customer customer = new Customer("YEONHO", "CHOI");

        //When
        Customer savedCustomer = repository.save(customer);

        //Then
        Customer entity = repository.findById(savedCustomer.getId()).get();
        assertThat(entity).isEqualTo(customer);
    }

    @Test
    @DisplayName("고객 조회 성공")
    void findAllTest() {
        //Given
        Customer customer1 = new Customer("first1", "Last1");
        repository.save(customer1);
        Customer customer2 = new Customer("first2", "Last2");
        repository.save(customer2);

        //When
        List<Customer> customers = repository.findAll();
        customers.forEach((customer) -> log.info("{} {} {}", customer.getId(), customer.getFirstName(), customer.getLastName()));

        //Then
        assertThat(customers.size()).isSameAs(2);
    }

    @Test
    @DisplayName("고객 정보 업데이트 성공")
    void updateTest() {
        //Given
        Customer customer = new Customer("first1", "Last1");
        Customer savedCustomer = repository.save(customer);

        //When
        savedCustomer.setFirstName("Update");
        repository.save(savedCustomer);

        //Then
        Customer updatedCustomer = repository.findById(savedCustomer.getId()).get();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("Update");
    }

    @Test
    @DisplayName("고객 삭제 성공")
    void deleteTest() {
        //Given
        Customer customer = new Customer("YEONHO", "CHOI");
        Customer savedCustomer = repository.save(customer);

        //When
        repository.deleteById(savedCustomer.getId());

        //Then
        Optional<Customer> findCustomer = repository.findById(savedCustomer.getId());
        assertThat(findCustomer.isEmpty()).isTrue();
    }
}