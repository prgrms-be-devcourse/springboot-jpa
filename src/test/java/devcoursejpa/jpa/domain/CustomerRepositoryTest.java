package devcoursejpa.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객_저장() {
        Customer customer = new Customer(1L, new Name("jiwoong", "kim"));

        customerRepository.save(customer);

        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getName()).isEqualTo(customer.getName());
    }

    @Test
    void 고객정보_업데이트() {
        Customer customer = new Customer(1L, new Name("jiwoong", "kim"));
        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.changeName(new Name("joomin", "cha"));
        customerRepository.save(savedCustomer);

        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getName()).isEqualTo(savedCustomer.getName());
    }

    @Test
    void 하나의_고객_조회() {
        Customer customer = new Customer(1L, new Name("jiwoong", "kim"));
        customerRepository.save(customer);

        Customer findCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(customer.getId()).isEqualTo(findCustomer.getId());
    }

    @Test
    void 리스트_조회() {
        Customer customer1 = new Customer(1L,  new Name("jiwoong", "kim"));
        Customer customer2 = new Customer(2L,  new Name("jiwoong", "kim"));

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers.size()).isEqualTo(2);
    }

    @Test
    void 고객_단건_삭제() {
        Customer customer1 = new Customer(1L, new Name("jiwoong", "kim"));
        Customer customer2 = new Customer(2L,  new Name("jiwoong", "kim"));

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        customerRepository.delete(customer1);
        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers.size()).isEqualTo(1);
    }
}