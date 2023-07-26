package com.pgms.jpa.service;

import com.pgms.jpa.domain.customer.Customer;
import com.pgms.jpa.domain.customer.repository.DefaultCustomerRepository;
import com.pgms.jpa.global.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class CustomerService {

    private final DefaultCustomerRepository defaultCustomerRepository;

    public CustomerService(DefaultCustomerRepository defaultCustomerRepository) {
        this.defaultCustomerRepository = defaultCustomerRepository;
    }

    @Transactional
    public Long join(Customer customer) {
        return defaultCustomerRepository.save(customer);
    }

    public Customer findOne(Long id) {
        Customer findCustomer = defaultCustomerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NO_SUCH_ELEMENT.getMsg()));

        return findCustomer;
    }

    public List<Customer> findAll() {
        return defaultCustomerRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        defaultCustomerRepository.delete(id);
    }

    @Transactional
    public void deleteAll() {
        defaultCustomerRepository.deleteAll();
    }
}
