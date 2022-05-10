package prgrms.assignment.jpa.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgrms.assignment.jpa.domain.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer = new Customer("seonghyeon", "kim");

    @BeforeEach
    void setup() {
        customerRepository.save(customer);
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void testSave() {
        var beforeSave = customerRepository.count();
        var newCustomer = new Customer("test", "kim");
        var savedCustomer = customerRepository.save(newCustomer);

        var afterSave = customerRepository.count();

        assertThat(afterSave).isEqualTo(beforeSave + 1);
        assertThat(savedCustomer).isSameAs(newCustomer);
    }

    @Test
    @DisplayName("회원을 조회한다.")
    void testSearchById() {
        var retrievedCustomer = customerRepository.findById(customer.getId());

        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get()).isSameAs(customer);
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void testSearchAll() {
        var retrievedCustomers = customerRepository.findAll();

        assertThat(retrievedCustomers.size()).isEqualTo(1);
        assertThat(retrievedCustomers).contains(customer);
    }

    @Test
    @DisplayName("회원을 업데이트한다.")
    void testUpdate() {
        var beforeUpdate = customerRepository.findById(customer.getId()).orElse(null);
        assert beforeUpdate != null;
        beforeUpdate.updateName("new-seonghyeon", "new-kim");

        var afterUpdate = customerRepository.findById(customer.getId()).orElse(null);
        assert afterUpdate != null;

        assertThat(afterUpdate.getFirstName()).isEqualTo("new-seonghyeon");
        assertThat(afterUpdate.getLastName()).isEqualTo("new-kim");
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    void testDeleteById() {
        var beforeDelete = customerRepository.count();
        customerRepository.deleteById(customer.getId());
        var afterDelete = customerRepository.count();

        assertThat(afterDelete).isEqualTo(beforeDelete - 1);

        var retrievedCustomer = customerRepository.findById(customer.getId());

        assertThat(retrievedCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("모든 회원을 삭제한다.")
    void testDeleteAll() {
        var beforeDelete = customerRepository.count();

        assertThat(beforeDelete).isGreaterThan(0);

        customerRepository.deleteAll();
        var afterDelete = customerRepository.count();

        assertThat(afterDelete).isEqualTo(0);
    }
}