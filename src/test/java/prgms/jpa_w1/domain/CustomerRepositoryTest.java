package prgms.jpa_w1.domain;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.jpa_w1.config.domain.Customer;
import prgms.jpa_w1.config.domain.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    private static List<Customer> customerList;

    @BeforeAll
    static void beforeAll() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("oh");
        customer1.setLastName("sehan");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("five");
        customer2.setLastName("hanse");

        customerList = List.of(customer1, customer2);
    }

    @Nested
    class 엔티티_저장_테스트 {
        @Test
        void 엔티티_저장_확인() {
            // Given
            Customer customer = new Customer();
            customer.setId(1L);
            customer.setFirstName("oh");
            customer.setLastName("sehan");

            // When
            customerRepository.save(customer);

            // Then
            Customer selectedEntity = customerRepository.findById(1L).get();
            assertThat(selectedEntity.getId()).isEqualTo(1L);
            assertThat(selectedEntity.getFirstName()).isEqualTo(customer.getFirstName());
        }

        @Test
        void 엔티티_아이디_중복저장_확인() {
            // Given
            Customer customer1 = new Customer();
            customer1.setId(1L);
            customer1.setFirstName("oh");
            customer1.setLastName("sehan");

            Customer customer2 = new Customer();
            customer2.setId(1L);
            customer2.setFirstName("five");
            customer2.setLastName("hanse");

            // When
            customerRepository.save(customer1);
            customerRepository.save(customer2);

            // Then
            List<Customer> customerList = customerRepository.findAll();
            Customer entity = customerList.get(0);

            assertThat(customerList.size()).isEqualTo(1);
            assertThat(entity.getId()).isEqualTo(1L);
        }
    }

    @Nested
    class 엔티티_조회_테스트 {
        @Test
        void 단건조회_확인() {
            // Given
            Customer customer = new Customer();
            customer.setId(1L);
            customer.setFirstName("oh");
            customer.setLastName("sehan");
            customerRepository.save(customer);

            // When
            Customer selected = customerRepository.findById(customer.getId()).get();

            // Then
            assertThat(customer.getId()).isEqualTo(selected.getId());
        }

        @Test
        void 단건조회를_데이터가_없는_경우() {
            Assertions.assertThrows(NoSuchElementException.class, () -> customerRepository.findById(1L).get());
        }

        @Test
        void 리스트조회_확인() {
            // Given
            customerRepository.saveAll(customerList);

            // When
            List<Customer> selectedCustomers = customerRepository.findAll();

            // Then
            assertThat(selectedCustomers.size()).isEqualTo(2);
        }
    }

    @Test
    void 고객정보가_수정_확인() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("oh");
        customer.setLastName("sehan");
        Customer entity = customerRepository.save(customer);

        // When
        entity.setFirstName("five");
        entity.setLastName("hanse");

        // Then
        Customer selectedEntity = customerRepository.findById(1L).get();
        assertThat(selectedEntity.getId()).isEqualTo(1L);
        assertThat(selectedEntity.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(selectedEntity.getLastName()).isEqualTo(entity.getLastName());
    }

    @Nested
    class 엔티티_삭제_테스트 {
        @Test
        void 모든_엔티티_삭제() {
            // Given
            customerRepository.saveAll(customerList);

            // When
            customerRepository.deleteAll();
            List<Customer> customerList = customerRepository.findAll();

            // Then
            assertThat(customerList.size()).isEqualTo(0);
        }

        @Test
        void 특정_엔티티_삭제() {
            // Given
            customerRepository.saveAll(customerList);
            Customer customer = customerRepository.findById(1L).get();

            // When
            customerRepository.delete(customer);

            // Then
            List<Customer> allEntity = customerRepository.findAll();
            assertThat(allEntity.size()).isEqualTo(1);
        }
    }
}
