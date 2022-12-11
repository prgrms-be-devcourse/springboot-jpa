package com.prgrms.jpa.jpa1;

import com.prgrms.jpa.jpa1.domain.Customer;
import com.prgrms.jpa.jpa1.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void INSERT_TEST() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("surin");
        customer.setLastName("lee");

        repository.save(customer);

        Customer findCustomer = repository.findById(1L).get();

        assertThat(findCustomer.getFirstName())
                .isEqualTo(
                        customer.getFirstName());

        assertThat(findCustomer.getLastName())
                .isEqualTo(
                        customer.getLastName());
    }

    @Test
    @Transactional
    @DisplayName("고객의 이름을 수정할 수 있다.")
    void UPDATE_TEST() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("surin");
        customer.setLastName("lee");

        repository.save(customer);

        Customer beforeChangeCusotmer = repository.findById(1L).get();
        beforeChangeCusotmer.setFirstName("su");
        beforeChangeCusotmer.setLastName("rin");

        Customer afterChangeCustomer = repository.findById(1L).get();

        assertThat(beforeChangeCusotmer.getFirstName())
                .isEqualTo(
                        afterChangeCustomer.getFirstName());
        assertThat(beforeChangeCusotmer.getLastName())
                .isEqualTo(
                        afterChangeCustomer.getLastName());
    }

    @Test
    @DisplayName("id를 이용하여 한명의 고객 정보를 찾을 수 있다.")
    void FIND_ONE_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("surin");
        customer.setLastName("lee");
        repository.save(customer);

        // When
        Customer findCustomer = repository.findById(customer.getId()).get();

        // Then
        assertThat(customer.getId())
                .isEqualTo(
                        findCustomer.getId());
    }

    @Test
    @DisplayName("모든 고객 정보를 조회할 수 있다.")
    void FIND_LIST_TEST() {
        // Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("surin");
        customer1.setLastName("lee");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("merong");
        customer2.setLastName("lee");


        repository.saveAll(
                Lists.newArrayList(customer1, customer2));

        // When
        List<Customer> findAllCustomers = repository.findAll();

        // Then
        assertThat(findAllCustomers.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("id를 통해 한명의 고객을 삭제할 수 있다.")
    void DELETE_BY_ID_TEST() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("surin");
        customer.setFirstName("lee");

        repository.save(customer);

        // when
        repository.deleteById(1L);

        // then
        assertThat(repository.findById(1L))
                .isEqualTo(
                        Optional.empty());
    }

    @Test
    @DisplayName("모든 고객을 삭제할 수 있다.")
    void DELETE_ALL_TEST() {
        // given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("surin");
        customer1.setLastName("lee");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("merong");
        customer2.setLastName("lee");

        repository.saveAll(
                Lists.newArrayList(customer1, customer2));

        // when
        repository.deleteAll();

        // then
        assertThat(repository.findAll()
                .size())
                .isEqualTo(0);
    }
}
