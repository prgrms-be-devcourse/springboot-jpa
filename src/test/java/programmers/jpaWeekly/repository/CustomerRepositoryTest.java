package programmers.jpaWeekly.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import programmers.jpaWeekly.customer.entity.Customer;
import programmers.jpaWeekly.customer.repository.CustomerRepository;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void saveTest() {
        // Given
        Customer customer = new Customer("kim", "yeseul");

        // When
        customerRepository.save(customer);

        // Then
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void updateTest() {
        // Given
        Customer customer = new Customer("kim", "yeseul");
        customerRepository.save(customer);

        // When
        customer.updateCustomerName("Jo", "HeeJo");

        // Then
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void findByIdTest() {
        // Given
        Customer customer = new Customer("kim", "yeseul");
        customerRepository.save(customer);

        // When
        Customer foundCustomer = customerRepository.findById(customer.getId()).get();

        // Then
        assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("customer 삭제 후 해당 id로 조회 시 예외가 발생한다.")
    void deleteTest() {
        // Given
        Customer customer = new Customer("kim", "yeseul");
        customerRepository.save(customer);

        // When
        customerRepository.deleteById(customer.getId());

        // Then
        assertThatThrownBy(() -> customerRepository.findById(customer.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }
}
