package com.kdt.lecture.repository;

import com.kdt.lecture.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer customerCreator() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("yongcheol");
        customer.setLastName("kim");
        return customer;
    }

    private List<Customer> customersCreator() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("hoho");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("haha");
        customer2.setLastName("lee");

        Customer customer3 = new Customer();
        customer3.setId(3L);
        customer3.setFirstName("huhu");
        customer3.setLastName("park");

        return List.of(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("jpa를 사용해서 insert가 잘 되는지 테스트")
    void jpaSaveTest() {
        // Given
        Customer customer = customerCreator();

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        assertThat(entity).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("jpa를 사용해서 값이 update가 잘 되는지 테스트")
    void jpaUpdateTest() {
        // Given
        Customer customer = customerCreator();
        repository.save(customer);

        //When
        Customer entity = repository.findById(1L).get();
        entity.setFirstName("yongc");

        // Then
        Customer updated = repository.findById(1L).get();
        assertThat(customer).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    @DisplayName("jpa를 사용해서 저장된 값 전부 불러오기 테스트")
    void jpaFindAllTest() {
        // Given
        List<Customer> customers = customersCreator();
        repository.saveAll(customers);

        //When
        List<Customer> entities = repository.findAll();

        // Then
        assertThat(customers).usingRecursiveComparison().isEqualTo(entities);
    }

    @Test
    @DisplayName("jpa를 사용해서 ID를 기준으로 칼럼 불러오기 테스트")
    void jpaFindByIdTest() {
        // Given
        Customer customer = customerCreator();
        repository.save(customer);

        //When
        Customer entity = repository.findById(customer.getId()).get();

        // Then
        assertThat(customer).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    @DisplayName("jpa를 사용해서 ID를 기준으로 삭제가 잘 되는지 테스트")
    void jpaDeleteByIdTest() {
        // Given
        Customer customer = customerCreator();
        repository.save(customer);

        //When
        repository.deleteById(customer.getId());
        // Then
        assertThat(repository.existsById(customer.getId())).isFalse();
    }

    @Test
    @DisplayName("jpa를 사용해서 테이블 데이터를 전부 삭제가 잘되는지 테스트")
    void jpaDeleteAllTest() {
        // Given
        List<Customer> customers = customersCreator();
        repository.saveAll(customers);

        //When
        repository.deleteAll();
        List<Customer> entities = repository.findAll();

        // Then
        assertThat(entities).isEmpty();
    }

}