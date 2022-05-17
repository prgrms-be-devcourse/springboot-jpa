package org.prgrms.springbootjpa.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    private final Customer customer = new Customer();

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
    }

    @BeforeEach
    void setUp(){
        customer.setId(1L);
        customer.setFirstName("seungeun");
        customer.setLastName("kim");
        customerRepository.save(customer);
    }

    @Test
    void 고객정보_저장(){
        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setFirstName("julie");
        newCustomer.setLastName("kim");

        customerRepository.save(newCustomer);

        Customer selected = customerRepository.findById(2L).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        assertThat(selected.getId()).isEqualTo(2L);
        assertThat(selected.getFirstName()).isEqualTo(newCustomer.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(newCustomer.getLastName());
    }

    @Test
    void 고객정보_수정(){
        customer.setFirstName("Bin");
        customer.setLastName("Foo");

        customerRepository.save(customer);

        Customer selected = customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        assertThat(selected.getId()).isEqualTo(selected.getId());
        assertThat(selected.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(selected.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void 단건조회_확인(){
        Customer selected = customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        assertThat(selected.getId()).isEqualTo(selected.getId());
    }

    @Test
    void 리스트조회_확인(){
        Customer customer1 = new Customer();
        customer1.setId(2L);
        customer1.setFirstName("yebin");
        customer1.setLastName("choi");

        Customer customer2 = new Customer();
        customer2.setFirstName("chaehi");
        customer2.setLastName("lee");

        customerRepository.saveAll(Lists.newArrayList(customer1, customer2));

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size()).isEqualTo(3);
    }

    @Test
    void 고객정보_삭제(){
        customerRepository.deleteById(1L);

        Optional<Customer> selected = customerRepository.findById(1L);

        assertThat(selected).isEmpty();
    }

}