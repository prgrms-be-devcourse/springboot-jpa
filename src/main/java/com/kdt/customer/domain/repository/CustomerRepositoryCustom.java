package com.kdt.customer.domain.repository;

import com.kdt.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CustomerRepositoryCustom implements CustomerRepository {
    private final JpaTemplate jpaTemplate;

    @Override
    public Customer save(Customer customer) {
        return jpaTemplate.executeInTransaction(entityManager ->  {
            entityManager.persist(customer);
            return customer;
        });
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(jpaTemplate.executeInTransaction(entityManager
                -> entityManager.find(Customer.class, id)));
    }

    @Override
    public void update(Long id, String firstName, String lastName) {
        jpaTemplate.executeInTransaction(entityManager -> {
            Customer customer = entityManager.find(Customer.class, id);
            customer.updateName(firstName, lastName);
            return null;
        });
    }

    @Override
    public void deleteById(Long id) {
        jpaTemplate.executeInTransaction(entityManager -> {
            Customer customer = entityManager.find(Customer.class, id);
            entityManager.remove(customer);
            return null;
        });
    }
}
