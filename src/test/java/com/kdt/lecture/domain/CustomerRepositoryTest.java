package com.kdt.lecture.domain;

import com.kdt.lecture.domain.domainV1.Customer;
import com.kdt.lecture.domain.domainV1.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CRUD test
 */
@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
        System.out.println("repository = " + repository.findAll().size());
    }

    private final String FIRST_NAME = "firstName";
    private final String NEW_FIRST_NAME = "newFirstName";

    @Test
    public void C() throws Exception {

        //given
        Customer customer = new Customer(1L, FIRST_NAME, "lastName");

        //when
        Customer save = repository.save(customer);

        //then
        assertThat(repository.findAll().size()).isEqualTo(1);

    }

    @Test
    public void 같은_Id의_고객_저장하면_같은_고객_취급() throws Exception {

        //given
        Customer customer = new Customer(1L, FIRST_NAME, "lastName");
        Customer customer2 = new Customer(1L, NEW_FIRST_NAME, "new lastName");

        //when
        Customer save = repository.save(customer);
        repository.save(customer2);

        //then
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void R() throws Exception {

        //given
        Customer customer = new Customer(1L, FIRST_NAME, "lastName");
        Customer save = repository.save(customer);

        //when
        Optional<Customer> byId = repository.findById(1L);

        //then
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(byId.get().getLastName()).isEqualTo("lastName");
    }

    @Test
    public void U() throws Exception {

        //given
        Customer customer = new Customer(1L, FIRST_NAME, "lastName");
        Customer save = repository.save(customer);

        //when
        save.changeFirstName(NEW_FIRST_NAME);

        //then
        assertThat(repository.findById(1L).get().getFirstName()).isEqualTo(NEW_FIRST_NAME);
    }

    @Test
    public void D() throws Exception {

        //given
        Customer customer = new Customer(1L, FIRST_NAME, "lastName");
        Customer save = repository.save(customer);
        assertThat(repository.findAll().size()).isEqualTo(1);

        //when
        repository.delete(save);

        //then

        assertThat(repository.findById(1L)).isEmpty();
        assertThat(repository.findById(1L).isEmpty()).isTrue();
        assertThat(repository.findAll().size()).isEqualTo(0);
    }
}