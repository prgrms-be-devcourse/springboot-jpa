package com.devcourse.mission.domain.customer.repository;

import com.devcourse.mission.domain.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void save_customer() {
        // given
        Customer customer = getCustomer();
        List<Customer> all = customerRepository.findAll();
        System.out.println("all.size() = " + all.size());

        // when
        Customer savedCustomer = customerRepository.save(customer);

        // then
        assertThat(savedCustomer)
                .hasFieldOrPropertyWithValue("age", 20)
                .hasFieldOrPropertyWithValue("address", "도시")
                .hasFieldOrPropertyWithValue("name", "박현서");
    }

    @Test
    void find_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(savedCustomer);
    }

    @Test
    void merge_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer updateCustomer = new Customer(savedCustomer.getId(), "서현박", "시골", 3);
        Customer updatedCustomer = customerRepository.save(updateCustomer);
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(updatedCustomer);
    }

    @Test
    void dirty_checking_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        savedCustomer.changeName("서현박");
        savedCustomer.changeAddress("시골");
        savedCustomer.changeAge(3);
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(savedCustomer);
    }

    @Test
    void delete_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        customerRepository.deleteById(savedCustomer.getId());
        Optional<Customer> mayBeCustomer = customerRepository.findById(savedCustomer.getId());

        // then
        assertThat(mayBeCustomer).isNotPresent();
    }

    private Customer getCustomer() {
        return new Customer("박현서", "도시", 20);
    }
}