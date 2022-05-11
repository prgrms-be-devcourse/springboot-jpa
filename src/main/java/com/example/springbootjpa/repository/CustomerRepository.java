package com.example.springbootjpa.repository;


import com.example.springbootjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
