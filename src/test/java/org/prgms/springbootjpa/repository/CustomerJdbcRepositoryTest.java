package org.prgms.springbootjpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.customer.repository.CustomerJdbcRepository;
import org.prgms.springbootjpa.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CustomerJdbcRepositoryTest {
    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute(DROP_TABLE_SQL);
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    Customer insertCustomer = new Customer(1L, "hyeonji", "park");

    @Test
    @DisplayName("customer를 추가할 수 있다.")
    void save() {
        int row = customerJdbcRepository.save(insertCustomer);

        assertThat(row, is(1));
    }

    @Test
    @DisplayName("id로 customer를 조회할 수 있다.")
    void findById() {
        customerJdbcRepository.save(insertCustomer);

        Customer customer = customerJdbcRepository.findById(insertCustomer.getId());

        assertThat(insertCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("customer를 수정할 수 있다.")
    void update() {
        customerJdbcRepository.save(insertCustomer);
        insertCustomer.changeFirstName("hyeonz");

        Customer customer = customerJdbcRepository.update(insertCustomer);

        assertThat(insertCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("모든 customer를 조회할 수 있다.")
    void findAll() {
        customerJdbcRepository.save(insertCustomer);
        customerJdbcRepository.save(new Customer(2L, "hyeonji", "kim"));

        List<Customer> customers = customerJdbcRepository.findAll();

        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("id로 customer를 삭제할 수 있다.")
    void deleteById() {
        customerJdbcRepository.save(insertCustomer);

        customerJdbcRepository.deleteById(insertCustomer.getId());
        List<Customer> customers = customerJdbcRepository.findAll();

        assertThat(customers, empty());
    }
}
