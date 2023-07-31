package kr.co.prgrms.jpaintro.domain;

import kr.co.prgrms.jpaintro.domain.customer.Customer;
import kr.co.prgrms.jpaintro.domain.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    private Customer customer1;

    @BeforeAll
    void beforeAll() {
        customer1 = new Customer("예성", "고");
    }

    @Test
    @DisplayName("고객 저장 성공 테스트")
    void saveCustomerTest() {
        // when
        Customer savedCustomer = repository.save(customer1);

        // then
        assertThat(customer1.getFirstName())
                .isEqualTo(savedCustomer.getFirstName());
        assertThat(customer1.getLastName())
                .isEqualTo(savedCustomer.getLastName());
    }

    @Test
    @DisplayName("고객 조회 성공 테스트")
    void findCustomerTest() {
        // given
        Customer savedCustomer = repository.save(customer1);

        // when
        Optional<Customer> byId = repository.findById(savedCustomer.getId());
        Customer foundCustomer = byId.get();

        // then
        assertThat(customer1.getFirstName())
                .isEqualTo(foundCustomer.getFirstName());
        assertThat(customer1.getLastName())
                .isEqualTo(foundCustomer.getLastName());
    }

    @Test
    @DisplayName("모든 고객 조회 성공 테스트")
    void findAllCustomersTest() {
        // given
        Customer customer2 = new Customer("애송이", "고");

        // when
        repository.save(customer1);
        repository.save(customer2);
        List<Customer> findAll = repository.findAll();

        // then
        assertThat(findAll)
                .isNotEmpty();
        assertThat(findAll.size())
                .isEqualTo(2);
    }


    @Test
    @DisplayName("고객 이름 변경 성공 테스트")
    void updateCustomerTest() {
        // given
        Customer savedCustomer = repository.save(customer1);

        // when
        savedCustomer.changeFirstName("예예성성");
        savedCustomer.changeLastName("코");
        Optional<Customer> byId = repository.findById(savedCustomer.getId());
        Customer foundCustomer = byId.get();

        // then
        assertThat(savedCustomer.getFirstName())
                .isEqualTo(foundCustomer.getFirstName());
        assertThat(savedCustomer.getLastName())
                .isEqualTo(foundCustomer.getLastName());
    }

    @Test
    @DisplayName("고객 삭제 성공 테스트")
    void deleteCustomerTest() {
        // given
        Customer savedCustomer = repository.save(customer1);

        // when
        repository.deleteById(savedCustomer.getId());
        Optional<Customer> byId = repository.findById(customer1.getId());

        // then
        assertThat(byId).isEmpty();
    }
}
