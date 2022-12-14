package com.prgrms.jpamission.customer.repository;

import com.prgrms.jpamission.domain.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

//    @BeforeEach
//    void createCustomer() {
//        Customer customer = Customer.builder()
//                .firstName("go")
//                .lastName("seungwon")
//                .build();
//        customerRepository.save(customer);
//    }

    @AfterEach
    void cleanDB() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("커스토머를 생성한다.")
    void 커스토머_생성() {
        //given
        Customer customer = Customer.builder()
                .firstName("go")
                .lastName("seungwon")
                .build();

        //when
        customerRepository.save(customer);

        //then
        Optional<Customer> findCustomer = customerRepository.findByFirstName("go");
        assertEquals(findCustomer.get().getFirstName(), "go");
        assertEquals(findCustomer.get().getLastName(), "seungwon");
    }

    @Test
    @DisplayName("저장된 커스토머를 조회한다.")
    void 커스토머_조회() {
        //given
        Customer customer1 = Customer.builder()
                .firstName("go")
                .lastName("seungwon")
                .build();
        customerRepository.save(customer1);

        Customer customer2 = Customer.builder()
                .firstName("dev")
                .lastName("course")
                .build();
        customerRepository.save(customer2);

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertEquals(customers.size(), 2);
    }

    @Test
    @DisplayName("커스토머를 변경한다.")
    void 커스토머_업데이트() {
        //given
        Customer customer = Customer.builder()
                .firstName("go")
                .lastName("seungwon")
                .build();
        customerRepository.save(customer);

        //when
        customer.changeFirstName("dev");
        customerRepository.save(customer);

        //then
        assertEquals(customerRepository.findByFirstName("dev").get().getFirstName(), "dev");
    }

    @Test
    @DisplayName("커스토머를 삭제한다.")
    void 커스토머_삭제() {
        //given
        Customer customer1 = Customer.builder()
                .firstName("go")
                .lastName("seungwon")
                .build();
        customerRepository.save(customer1);

        Customer customer2 = Customer.builder()
                .firstName("dev")
                .lastName("course")
                .build();
        customerRepository.save(customer2);

        Customer customer3 = Customer.builder()
                .firstName("back")
                .lastName("end")
                .build();
        customerRepository.save(customer3);

        //when then
        customerRepository.deleteAll();
        assertEquals(0, customerRepository.findAll().size());
    }




}