package com.example.springbootjpa.customer.service;

import com.example.springbootjpa.customer.model.Customer;
import com.example.springbootjpa.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    Customer save(Customer customer){
        return repository.save(customer);
    }

    Optional<Customer> findById(Long id){
        return repository.findById(id);
    }

    List<Customer> findAll(){
        return repository.findAll();
    }

    Customer update(Customer customer){
        return customer;
    }

    void deleteById(Long id){
        repository.deleteById(id);
    }
    void deleteAll(){
        repository.deleteAll();
    }
}
