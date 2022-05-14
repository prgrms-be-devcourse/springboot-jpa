package com.dojinyou.devcourse.springbootjpa;

import com.dojinyou.devcourse.springbootjpa.repository.CustomerXmlMapper;
import com.dojinyou.devcourse.springbootjpa.repository.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
public class MybatisTest {

    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerXmlMapper customerMapper;

    @Test
    void save_test() {
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);

        customerMapper.save(new Customer(1L, "dojin", "you"));
        Customer customer = customerMapper.findById(1L);

        log.info("fulName : {} {}", customer.getFirstName(), customer.getLastName());
    }
}
