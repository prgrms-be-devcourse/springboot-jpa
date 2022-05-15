package com.kdt.lecture.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.lecture.domain.Customer;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("CRUD 구현 Test")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void Customer_Create_Test() {
        //given
        Customer customer = new Customer(1L, "hunki", "kim");
        //when
        customerRepository.save(customer); // 생성해서 레포에 넣기
        //then
        Optional<Customer> selectedCustomer = customerRepository.findById(1L); // Optional로 나와서
        assertThat(selectedCustomer).isNotEmpty();
    }

    @Test
    void Customer_Read_Test() {
        //given
        Customer customer = new Customer(1L, "hunki", "kim");
        customerRepository.save(customer);
        //when
        Optional<Customer> selectedCustomer = customerRepository.findById(1L);
        //then
        assertThat(selectedCustomer).isNotEmpty();
        assertThat(selectedCustomer.get().getId()).isEqualTo(customer.getId());
        assertThat(selectedCustomer.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(selectedCustomer.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void Customer_Update_Test() {
        //given
        Customer customer = new Customer(1L, "hunki", "kim");
        customerRepository.save(customer);
        //when
        customer.setLastName("KIM");
        customer.setFirstName("HUNKI");
        customerRepository.save(customer);
        Optional<Customer> updatedCustomer = customerRepository.findById(
            customer.getId()); //PR Point1 , 플러쉬는 이 때 되는건지 궁금합니다!
        //then
        assertThat(updatedCustomer).isNotEmpty();
        assertThat(updatedCustomer.get().getLastName()).isEqualTo(customer.getLastName());
        assertThat(updatedCustomer.get().getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void Customer_FindAll_Test() {
        //given
        Customer customer1 = new Customer(1L, "hunki", "kim");
        Customer customer2 = new Customer(2L, "HUNKI", "KIM");
        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));
        //when
        List<Customer> customerList = customerRepository.findAll();
        //then
        assertThat(customerList.size()).isEqualTo(2);
    }

    @Test
    void Customer_Delete_Test() {
        //given
        Customer customer = new Customer(1L, "hunki", "kim");
        customerRepository.save(customer);
        //when
        customerRepository.deleteById(customer.getId());
        //then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }
}