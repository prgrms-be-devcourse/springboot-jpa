package com.dojinyou.devcourse.springbootjpa.practice.repository;

import com.dojinyou.devcourse.springbootjpa.practice.repository.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerXmlMapper {
    void save(Customer customer);
    Customer findById(long id);
}
