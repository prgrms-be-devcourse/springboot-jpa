package com.dojinyou.devcourse.springbootjpa.repository;

import com.dojinyou.devcourse.springbootjpa.repository.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerXmlMapper {
    void save(Customer customer);
    Customer findById(long id);
}
