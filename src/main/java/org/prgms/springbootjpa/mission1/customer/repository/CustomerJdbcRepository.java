package org.prgms.springbootjpa.mission1.customer.repository;

import org.prgms.springbootjpa.mission1.customer.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = (resultSet, i) ->
            new Customer(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")
            );

    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (id, first_name, last_name) VALUES(?, ?, ?)",
                customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    public Customer findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = ?", customerRowMapper, id);
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    public Customer update(Customer customer) {
        jdbcTemplate.update("UPDATE customers SET first_name = ?, last_name = ? WHERE id = ?",
                customer.getFirstName(), customer.getLastName(), customer.getId());

        return findById(customer.getId());
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM customers WHERE id = ?", id);
    }
}
