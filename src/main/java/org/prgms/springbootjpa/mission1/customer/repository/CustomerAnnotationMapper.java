package org.prgms.springbootjpa.mission1.customer.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.prgms.springbootjpa.mission1.customer.domain.Customer;

import java.util.List;

@Mapper
public interface CustomerAnnotationMapper {
    @Update("INSERT INTO customers (id, first_name, last_name) VALUES(#{id}, #{firstName}, #{lastName})")
    void save(Customer customer);

    @Select("SELECT * FROM customers WHERE id = #{id}")
    Customer findById(long id);

    @Select("SELECT * FROM customers")
    List<Customer> findAll();

    @Update("UPDATE customers SET first_name = #{firstName}, last_name = #{lastName} WHERE id = #{id}")
    void update(Customer customer);

    @Update("DELETE FROM customers WHERE id = #{id}")
    void deleteById(long id);
}
