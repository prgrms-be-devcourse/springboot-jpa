package devcoursejpa.jpa.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        Customer customer1 = new Customer(1L, new Name("jiwoong", "kim"));
        Customer customer2 = new Customer(2L, new Name("jiwoong", "kim"));

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));
    }

    @Test
    void 고객_저장() {
        Customer findCustomer = customerRepository.findById(1L).get();

        assertAll(
                () -> assertThat(findCustomer.getId()).isEqualTo(1L),
                () -> assertThat(findCustomer.getName()).isEqualTo("jiwoong")
        );
    }

    @Test
    void 고객정보_업데이트() {
        Customer findCustomer = customerRepository.findById(1L).get();
        Name name = new Name("joomin", "cha");

        findCustomer.changeName(name);

        assertAll(
                () -> assertThat(findCustomer.getId()).isEqualTo(1L),
                () -> assertThat(findCustomer.getName()).isEqualTo(name)
        );
    }

    @Test
    void 하나의_고객_조회() {
        Customer findCustomer = customerRepository.findById(1L).get();

        assertThat(findCustomer).isNotNull();
    }

    @Test
    void 리스트_조회() {
        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers).hasSize(2);
    }

    @Test
    void 고객_단건_삭제() {
        Customer findCustomer = customerRepository.findById(1L).get();
        customerRepository.delete(findCustomer);

        List<Customer> findCustomers = customerRepository.findAll();

        assertThat(findCustomers).hasSize(1);
    }
}