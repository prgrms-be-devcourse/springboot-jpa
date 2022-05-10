package com.kdt.lecture;

import com.kdt.lecture.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    @DisplayName("CUSTOMER SAVE TEST")
    void saveTest(){

        //given
        var customer = new Customer();

        customer.setId(1L);
        customer.setFirstName("hj");
        customer.setLastName("kim");

        //when
        repository.save(customer);

        //then
        var findOne = repository.findById(customer.getId()).get();
        log.info("Found Customer {} {}", findOne.getFirstName(), findOne.getLastName());
    }
}