package com.example.jpa.customer;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("test")
@Repository
@RequiredArgsConstructor
public class JpaCustomerRepository {

    private final EntityManager em;

    void save(Customer customer){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(customer);

        transaction.commit();
    }

    List<Customer> findAll() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        List<Customer> customers = em.createQuery("select c from Customer c", Customer.class).getResultList();

        transaction.commit();
        return customers;
    }

    Optional<Customer> findById(Long id){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customerById = em.find(Customer.class, id);
        Optional<Customer> customer = Optional.ofNullable(customerById);

        transaction.commit();
        return customer;
    }

    void update(Long id, CustomerUpdateRequestDto updatedCustomer){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customerById = em.find(Customer.class, id);
        customerById.updateInfo(updatedCustomer);

        transaction.commit();
    }

    void delete(Long id){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customerById = em.find(Customer.class, id);
        em.remove(customerById);

        transaction.commit();
    }

}
