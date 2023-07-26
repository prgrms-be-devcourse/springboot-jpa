package org.prgms.springbootjpa.mission1.customer.repository;

import org.apache.ibatis.annotations.Mapper;
import org.prgms.springbootjpa.mission1.customer.domain.Customer;

import java.util.List;

@Mapper
public interface CustomerXmlMapper {
    void save(Customer customer);

    Customer findById(Long id);

    List<Customer> findAll();

    void update(Customer customer);

    void deleteById(Long id);
}
