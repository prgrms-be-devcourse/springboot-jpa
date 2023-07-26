package org.prgms.springbootjpa.mission1.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootjpa.mission1.customer.domain.Customer;
import org.prgms.springbootjpa.mission1.customer.repository.CustomerAnnotationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerAnnotationMapperTest {
    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerAnnotationMapper customerAnnotationMapper;

    @BeforeEach
    void setup() {
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);

        customerAnnotationMapper.save(insertCustomer);
    }

    Customer insertCustomer = new Customer(1L, "hyeonji", "park");

    @Test
    @DisplayName("customer를 추가하고 조회할 수 있다.")
    void save() {
        Customer customer = customerAnnotationMapper.findById(insertCustomer.getId());

        assertThat(insertCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("customer를 수정할 수 있다.")
    void update() {
        insertCustomer.changeFirstName("hyeonz");

        customerAnnotationMapper.update(insertCustomer);

        Customer customer = customerAnnotationMapper.findById(insertCustomer.getId());

        assertThat(insertCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("모든 customer를 조회할 수 있다.")
    void findAll() {
        customerAnnotationMapper.save(new Customer(2L, "hyeonji", "kim"));

        List<Customer> customers = customerAnnotationMapper.findAll();

        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("id로 customer를 삭제할 수 있다.")
    void deleteById() {
        customerAnnotationMapper.deleteById(insertCustomer.getId());

        List<Customer> customers = customerAnnotationMapper.findAll();

        assertThat(customers, empty());
    }
}
