package org.prgms.springbootjpa.customer.repository;

import org.apache.ibatis.annotations.Mapper;
import org.prgms.springbootjpa.customer.domain.Customer;

import java.util.List;

@Mapper
public interface CustomerXmlMapper {
    void save(Customer customer);

    Customer findById(long id);

    List<Customer> findAll();

    void update(Customer customer);

    void deleteById(long id);
}
