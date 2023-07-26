package com.example.weeklyjpa;

import com.example.weeklyjpa.domain.customer.Customer;
import com.example.weeklyjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class JPATest {

    @Autowired
    CustomerRepository repository;

    Customer customer;
    Customer savedCustomer;

    @BeforeEach
    void setUp(){
        customer = new Customer("jaehyun","jo");
        savedCustomer = repository.save(customer);
    }

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보 생성에 성공한다.")
    void CREATE_TEST(){
        // then
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 정보 수정에 성공한다.")
    @Transactional // 영속성 컨텍스트 내에서 관리를 하겠다
    void UPDATE_TEST(){
        // when
        Customer foundCustomer = repository.findById(1L).orElseThrow(NullPointerException::new);
        foundCustomer.changeFirstName("hihi");
        foundCustomer.changeLastName("ju");

        // then
        Customer updatedCustomer = repository.findById(1L).orElseThrow(NullPointerException::new);
        assertThat(updatedCustomer.getLastName()).isEqualTo("ju");
        assertThat(updatedCustomer.getFirstName()).isEqualTo("hihi");
    }

    @Test
    @DisplayName("고객 정보 삭제에 성공한다.")
    void DELETE_TEST(){
        // when
        repository.deleteById(1L);

        // then
        assertThat(repository.existsById(1L)).isFalse();
    }
}
