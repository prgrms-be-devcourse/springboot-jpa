package com.pgms.jpa.domain;

import com.pgms.jpa.global.ErrorCode;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class DefaultCustomerRepository {

    private final EntityManager entityManager;

    public DefaultCustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long save(Customer customer) {
        entityManager.persist(customer);
        return customer.getId();
    }

    public Optional<Customer> findById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }

    public List<Customer> findAll() {
        List<Customer> customers = entityManager.createQuery("select c from Customer c", Customer.class)
                .getResultList();
        return customers;
    }

    public void delete(Long id) {
        Customer findCustomer = findById(id)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.NO_SUCH_ELEMENT.getMsg()));
        entityManager.remove(findCustomer);
    }

    public void deleteAll() {
        findAll().stream()
                .map(Customer::getId)
                .forEach(this::delete);
    }
}
