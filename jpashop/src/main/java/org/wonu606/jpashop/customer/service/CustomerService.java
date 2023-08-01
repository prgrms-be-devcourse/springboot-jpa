package org.wonu606.jpashop.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wonu606.jpashop.customer.domain.Customer;
import org.wonu606.jpashop.customer.domain.CustomerRepository;
import org.wonu606.jpashop.customer.service.dto.CustomerCreateData;
import org.wonu606.jpashop.customer.service.dto.CustomerResult;
import org.wonu606.jpashop.customer.service.dto.CustomerResults;
import org.wonu606.jpashop.customer.service.exception.CustomerNotFoundException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CustomerResult create(CustomerCreateData customerCreateData) {
        Customer creatingCustomer = new Customer(customerCreateData.firstName(),
                customerCreateData.lastName());
        Customer savedCustomer = repository.save(creatingCustomer);
        return new CustomerResult(
                savedCustomer.getId(),
                savedCustomer.getFirstName(),
                savedCustomer.getLastName());
    }

    public CustomerResult findById(Long id) {
        Optional<Customer> foundCustomer = repository.findById(id);
        return foundCustomer.map(c ->
                        new CustomerResult(
                                c.getId(),
                                c.getFirstName(),
                                c.getLastName()))
                .orElseThrow(CustomerNotFoundException::new);
    }

    public CustomerResults findAll() {
        List<Customer> foundCustomers = repository.findAll();
        return new CustomerResults(foundCustomers.stream()
                .map(c ->
                        new CustomerResult(
                                c.getId(),
                                c.getFirstName(),
                                c.getLastName()))
                .collect(Collectors.toList()));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException();
        }
        repository.deleteById(id);
    }
}
