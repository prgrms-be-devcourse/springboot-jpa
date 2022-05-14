package com.dojinyou.devcourse.springbootjpa.practice.repository;

import com.dojinyou.devcourse.springbootjpa.practice.repository.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
