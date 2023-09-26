package com.example.weeklyjpa.domain.customer;

import com.example.weeklyjpa.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerJPATest {

    @Autowired
    CustomerRepository repository;

    Customer customer;
    Customer savedCustomer;

    @BeforeEach
    void setUp(){
        customer = new Customer("jaehyun","jo");
    }

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보 생성에 성공한다.")
    void CREATE_TEST(){
        savedCustomer = repository.save(customer);

        // then
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 정보 수정에 성공한다.")
    @Transactional // 영속성 컨텍스트 내에서 관리를 하겠다
    void UPDATE_TEST(){
        savedCustomer = repository.save(customer);

        // when
        Customer foundCustomer = repository.findById(savedCustomer.getId()).orElseThrow(NullPointerException::new);
        foundCustomer.changeFirstName("hihi");
        foundCustomer.changeLastName("ju");

        // then
        Customer updatedCustomer = repository.findById(savedCustomer.getId()).orElseThrow(NullPointerException::new);
        assertThat(updatedCustomer.getLastName()).isEqualTo("ju");
        assertThat(updatedCustomer.getFirstName()).isEqualTo("hihi");
    }

    @Test
    @DisplayName("고객 정보 삭제에 성공한다.")
    void DELETE_TEST(){
        savedCustomer = repository.save(customer);

        // when
        repository.deleteById(savedCustomer.getId());

        // then
        assertThat(repository.existsById(savedCustomer.getId())).isFalse();
    }
}
