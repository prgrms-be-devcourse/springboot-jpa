package com.example.demo;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class PersistenceTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    void test1(){
        Customer customer = new Customer();
        customer.setFirstName("jung hyun");
        customer.setLastName("moon");
        assertThat(entityManager.contains(customer), is(false));

        Customer entity = customerRepository.save(customer);
        assertThat(entityManager.contains(entity), is(true));

        Customer entity1 = entityManager.find(Customer.class, entity.getCustomerId());
        Customer entity2 = customerRepository.findById(entity.getCustomerId()).get();

        assertThat(entityManager.contains(entity1), is(true));
        assertThat(entityManager.contains(entity2), is(true));

        assertThat(entity, is(entity1));
        assertThat(entity, is(entity2));
        assertThat(entity1, is(entity2));
    }
}
