package com.kdt.jpaproject.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    void clear() {
        repository.deleteAllInBatch();
    }

    @Test
    @DisplayName("Customer 를 create 한다.")
    public void create() throws Exception {
        // given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();


        // when
        repository.save(customer);

        // then
        Customer entity = repository.findById(1L).get();
        assertEquals(entity.getId(), 1L);
        assertEquals(entity.getFirstName(), "Lee");
        assertEquals(entity.getLastName(), "YongHoon");
        assertNotEquals(entity, customer); // 동일성 보장X
    }

    @Test
    @DisplayName("Customer 를 update 한다.")
    public void update() throws Exception {
        // given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        repository.save(customer);
        Customer findCustomer = repository.findById(1L).get();
        findCustomer.changeFirstName("Kim");
        findCustomer.changeLastName("KKang");
        repository.save(findCustomer);

        // then

        Customer entity = repository.findById(1L).get();
        assertEquals(entity.getId(), 1L);
        assertEquals(entity.getLastName(), "KKang");
        assertEquals(entity.getFirstName(), "Kim");
        assertEquals(entity, findCustomer); // 동일성 보장O
    }

    @Test
    @DisplayName("Customer 를 delete 한다.")
    public void delete() throws Exception {
        // given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Lee")
                .lastName("YongHoon")
                .build();

        // when
        repository.save(customer);
        repository.deleteById(1L);

        // then
        long size = repository.count();
        assertEquals(size, 0);
    }
}