package devcoursejpa.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void 고객_저장() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");

        customerRepository.save(customer);

        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void 고객정보_업데이트() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");
        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setFirstName("joomin");
        savedCustomer.setLastName("cha");
        customerRepository.save(savedCustomer);

        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getFirstName()).isEqualTo(savedCustomer.getFirstName());
        assertThat(findCustomer.getLastName()).isEqualTo(savedCustomer.getLastName());
    }

    @Test
    void 하나의_고객_조회() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jiwoong");
        customer.setLastName("kim");
        customerRepository.save(customer);

        Customer findCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(customer.getId()).isEqualTo(findCustomer.getId());
    }

    @Test
    void 리스트_조회() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("jiwoong");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("jiwoong");
        customer2.setLastName("kim");

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers.size()).isEqualTo(2);
    }

    @Test
    void 고객_단건_삭제() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("jiwoong");
        customer1.setLastName("kim");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("jiwoong");
        customer2.setLastName("kim");

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        customerRepository.delete(customer1);
        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers.size()).isEqualTo(1);
    }
}