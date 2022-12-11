package com.prgrms.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


import com.prgrms.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @DisplayName("고객을 생성한다")
    @Test
    void createCustomer() {

        //given
        Customer customer = new Customer(1L, "seo", "yewon");
        repository.save(customer);

        //when
        Customer createdCustomer = repository.findById(1L).get();

        //then
        assertEquals(customer, createdCustomer);

    }

    @DisplayName("전체 고객을 조회한다")
    @Test
    void getCustomerList() {

        //given
        Customer customer1 = new Customer(1L, "seo", "yewon1");
        Customer customer2 = new Customer(2L, "seo", "yewon1");
        repository.save(customer1);
        repository.save(customer2);

        //when
        List<Customer> customerList = repository.findAll();

        //then
        assertEquals(2, customerList.size());
        assertEquals(customer1, customerList.get(0));
        assertEquals(customer2, customerList.get(1));

    }

    @DisplayName("id가 존재한다면 해당 id를 가진 customer 객체를 리턴한다")
    @Test
    void findById() {

        //given
        final long id = 1L;
        Customer customer = new Customer(id, "seo", "yewon");
        repository.save(customer);

        //when
        Customer foundCustomer = repository.findById(1L).get();

        //then
        assertEquals(customer, foundCustomer);
    }

    @DisplayName("id가 존재하지 않는다면 빈 Optional 을 리턴한다")
    @Test
    void ifIdDoesntExist() {

        //given
        repository.deleteAll();

        //when
        Optional<Customer> foundCustomer = repository.findById(1L);

        //then
        assertEquals(Optional.empty(), foundCustomer);
    }

    @DisplayName("해당 id를 가진 고객의 firstName 을 수정할 수 있다.")
    @Test
    void updateFirstName() {

        //given
        Customer customer = new Customer(1L, "서", "예원");
        repository.save(customer);

        //when
        Customer savedCustomer = repository.findById(1L).get();
        savedCustomer.setFirstName("이");

        repository.save(savedCustomer);

        //then
        assertEquals(savedCustomer.getFirstName(), "이");
    }

    @DisplayName("해당 id를 가진 고객의 lastName 을 수정할 수 있다.")
    @Test
    void updateLastName() {

        //given
        Customer customer = new Customer(1L, "서", "예원");
        repository.save(customer);

        //when
        Customer savedCustomer = repository.findById(1L).get();
        savedCustomer.setLastName("수린");

        repository.save(savedCustomer);

        //then
        assertEquals(savedCustomer.getLastName(), "수린");
    }

    @DisplayName("저장되어있는 customer정보를 모두 삭제한다")
    @Test
    void deleteAll() {

        //given
        Customer customer1 = new Customer(1L, "서", "예원");
        Customer customer2 = new Customer(2L, "이", "수린");
        repository.save(customer1);
        repository.save(customer2);

        //when
        repository.deleteAll();

        //then
        assertEquals(repository.findAll().size(), 0);
    }

    @DisplayName("해당 id를 가진 고객의 정보를 삭제한다")
    @Test
    void deleteById() {

        //given
        Customer customer1 = new Customer(1L, "서", "예원");
        Customer customer2 = new Customer(2L, "이", "수린");
        repository.save(customer1);
        repository.save(customer2);

        //when
        repository.deleteById(1L);

        //then
        assertEquals(repository.findAll().size(), 1);
        assertEquals(repository.findById(1L), Optional.empty());

    }

    @DisplayName("존재하지 않는 id로 삭제할 경우 EmptyResultDataAccessException 을 던진다")
    @Test
    void deleteByIdIfNotExist() {

        //given
        Customer customer1 = new Customer(1L, "서", "예원");
        repository.save(customer1);

        //when&then
        assertThatThrownBy(() -> repository.deleteById(2L)).isInstanceOf(
            EmptyResultDataAccessException.class);

    }





}