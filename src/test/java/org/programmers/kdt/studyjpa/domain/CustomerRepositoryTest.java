package org.programmers.kdt.studyjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("고객을 생성할 수 있다")
    void createCustomer() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        //When
        var save = repository.save(customer);
        var findCustomer = repository.findById(1L);

        //Then
        assertThat(findCustomer).isPresent().contains(save);
    }

    @Test
    @DisplayName("생성한 고객들을 검색할 수 있다")
    void findCustomer() {
        //Given
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("elon");
        customer.setLastName("musk");

        Customer blackCustomer = new Customer();
        customer.setId(2L);
        customer.setFirstName("inchang");
        customer.setLastName("choi");
        List<Customer> customerList = List.of(customer, blackCustomer);

        //When
        repository.saveAll(customerList);
        var repositoryAll = repository.findAll();

        //Then
        assertThat(repositoryAll).hasSize(2);
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다")
    void updateCustomer() {
        //given
        Customer customer = new Customer();
        customer.setId(4L);
        customer.setFirstName("seokyeol");
        customer.setLastName("yoon");
        var saveCustomer = repository.save(customer);

        //when
        customer.setFirstName("minsoo");
        var findCustomer = repository.findById(4L);

        //then
        assertThat(findCustomer).isPresent().contains(saveCustomer);
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다")
    void deleteCustomer() {
        //given
        Customer customer = new Customer();
        customer.setId(5L);
        customer.setFirstName("jaemyeong");
        customer.setLastName("lee");
        repository.save(customer);

        //when
        repository.deleteById(customer.getId());
        var deleteCustomer = repository.findById(customer.getId());

        //then
        assertThat(deleteCustomer).isEmpty();
    }
}