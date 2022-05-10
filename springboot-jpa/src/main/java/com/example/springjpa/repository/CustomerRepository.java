package com.example.springjpa.repository;

import com.example.springjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String firstName);

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
