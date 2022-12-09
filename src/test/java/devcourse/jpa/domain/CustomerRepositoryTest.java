package devcourse.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void save() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("seonho");
        customer.setLastName("Kim");

        // when
        customerRepository.save(customer);

        // then
        Customer entity = customerRepository.findById(1L).get();
        log.info("{} {}", entity.getLastName(), entity.getFirstName());
    }

    @Test
    @DisplayName("여러 고객을 저장할 수 있다.")
    void saveAll() {
        // given
        List<Customer> customers = List.of(
                new Customer(1L, "first1", "last1"),
                new Customer(2L, "first2", "last2")
        );

        // when
        customerRepository.saveAll(customers);

        // then
        List<Customer> foundCustomers = customerRepository.findAll();
        int expectedSize = customers.size();
        int actualSize = foundCustomers.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void findAll() {
        // given
        Customer customer = new Customer(1L, "FIRST", "LAST");
        customerRepository.save(customer);

        // when
        List<Customer> foundCustomers = customerRepository.findAll();

        // then
        int expectedSize = 1;
        int actualSize = foundCustomers.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("고객의 이름을 변경할 수 있다.")
    void updateCustomerFirstName() {
        // given
        Customer customer = new Customer(1L, "BEFORE", "LAST");
        customerRepository.save(customer);

        // when
        customerRepository.updateCustomerFirstName(customer.getId(), "AFTER");

        // then
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        String actualFirstName = foundCustomer.getFirstName();
        String expectedLastName = "AFTER";

        assertThat(actualFirstName).isEqualTo(expectedLastName);
    }
}