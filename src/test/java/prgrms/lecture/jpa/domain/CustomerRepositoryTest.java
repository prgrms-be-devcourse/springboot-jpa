package prgrms.lecture.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.validation.*;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private CustomerRepository customerRepository;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void saveCustomer() {
        // Given
        Customer customer = new Customer("first", "last");
        // When
        customerRepository.save(customer);
        em.flush();
        em.clear();
        // Then
        Optional<Customer> savedCustomerOptional = customerRepository.findById(customer.getId());
        assertThat(savedCustomerOptional).isPresent();
        Customer savedCustomer = savedCustomerOptional.get();
        assertThat(savedCustomer.getFirstName()).isEqualTo("first");
        assertThat(savedCustomer.getLastName()).isEqualTo("last");
    }

    @Test
    public void testGetCustomer() {
        // Given
        Customer customer = new Customer("first", "last");
        customerRepository.save(customer);
        em.flush();
        em.clear();
        // When
        Optional<Customer> savedCustomerOptional = customerRepository.findById(customer.getId());
        // Then
        assertThat(savedCustomerOptional).isPresent();
        Customer savedCustomer = savedCustomerOptional.get();
        assertThat(savedCustomer.getFirstName()).isEqualTo("first");
        assertThat(savedCustomer.getLastName()).isEqualTo("last");
    }


    @Test
    public void updateCustomer() {
        // Given
        Customer customer = new Customer("first", "last");
        customerRepository.save(customer);
        // When
        Optional<Customer> savedCustomerOptional = customerRepository.findById(customer.getId());
        assertThat(savedCustomerOptional).isPresent();
        Customer savedCustomer = savedCustomerOptional.get();
        savedCustomer.update("changeFirst", "changeLast");
        customerRepository.save(savedCustomer);
        em.flush();
        em.clear();

        // Then
        Optional<Customer> updatedCustomerOptional = customerRepository.findById(customer.getId());
        assertThat(updatedCustomerOptional).isPresent();
        Customer updatedCustomer = updatedCustomerOptional.get();
        assertThat(updatedCustomer.getFirstName()).isEqualTo("changeFirst");
        assertThat(updatedCustomer.getLastName()).isEqualTo("changeLast");
    }

    @Test
    public void deleteCustomer() {
        // Given
        Customer customer = new Customer("John", "Doe");
        customerRepository.save(customer);
        // When
        customerRepository.delete(customer);
        em.flush();
        em.clear();

        // Then
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    public void invalidCustomer() {
        // Given
        Customer invalidCustomer = new Customer("12345", "!@#$@$");
        // When
        Set<ConstraintViolation<Customer>> violations = validator.validate(invalidCustomer);
        // Then
        assertEquals(2, violations.size());
    }
}
