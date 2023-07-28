package com.jhs.springbootjpa.repository;

import com.jhs.springbootjpa.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    @DisplayName("저장")
    void save() {
        Customer customer = new Customer(null, "hongseok", "Ju");
        Customer saveCustomer = repository.save(customer);

        assertThat(saveCustomer)
                .hasFieldOrPropertyWithValue("firstName", "hongseok")
                .hasFieldOrPropertyWithValue("lastName", "Ju");
    }

    @Test
    @DisplayName("조회")
    void select() {
        Customer customer = new Customer(null, "hongseok", "Ju");
        Customer saveCustomer = repository.save(customer);

        Customer findCustomer = repository.findById(saveCustomer.getId()).get();

        assertThat(findCustomer)
                .usingRecursiveComparison().isEqualTo(saveCustomer);
    }

    @Test
    @DisplayName("업데이트")
    void update() {
        Customer customer = new Customer(null, "hongseok", "Ju");
        Customer saveCustomer = repository.save(customer);

        saveCustomer.updateFirstName("HONGSEOK");

        Customer updateCustomer = repository.findById(saveCustomer.getId()).get();

        assertThat(updateCustomer)
                .hasFieldOrPropertyWithValue("firstName", "HONGSEOK");
    }

    @Test
    @DisplayName("삭제")
    void delete() {
        Customer customer = new Customer(null, "hongseok", "Ju");
        Customer saveCustomer = repository.save(customer);

        repository.delete(saveCustomer);

        assertThat(repository.findById(saveCustomer.getId()))
                .isEmpty();
    }

}
