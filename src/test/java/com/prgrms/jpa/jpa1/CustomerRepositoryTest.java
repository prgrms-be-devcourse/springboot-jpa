package com.prgrms.jpa.jpa1;

import com.prgrms.jpa.jpa1.domain.Customer;
import com.prgrms.jpa.jpa1.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
public class CustomerRepositoryTest {

    private static final String firstName = "lee";
    private static final String lastName = "surin";

    @Autowired
    CustomerRepository repository;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void INSERT_TEST() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        repository.save(customer);

        Customer findCustomer = repository.findById(1L).get();

        String getFirstName = findCustomer.getFirstName();
        String getLastName = findCustomer.getLastName();

        assertThat(getFirstName).isEqualTo(firstName);
        assertThat(getLastName).isEqualTo(lastName);
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다.")
    void UPDATE_TEST() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        repository.save(customer);

        Customer beforeChangeCustomer = repository.findById(1L).get();

        String changeFirstName = "su";
        String changeLastName = "rin";

        beforeChangeCustomer.setFirstName("su");
        beforeChangeCustomer.setLastName("rin");

        Customer afterChangeCustomer = repository.findById(1L).get();

        String findFirstName = afterChangeCustomer.getFirstName();
        String findLastName = afterChangeCustomer.getLastName();

        assertThat(changeFirstName).isEqualTo(findFirstName);
        assertThat(changeLastName).isEqualTo(findLastName);
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
        Long id = customer.getId();
        Long findCustomerId = findCustomer.getId();

        assertThat(findCustomerId).isEqualTo(id);
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
        int realSize = findAllCustomers.size();
        int expectedSize = 2;

        assertThat(realSize).isEqualTo(expectedSize);
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
        Optional<Customer> findCustomer = repository.findById(1L);

        assertThat(findCustomer).isEqualTo(
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
        List<Customer> findCustomers = repository.findAll();
        assertThat(findCustomers).isEmpty();
    }
}
