package com.springbootjpa.domain;

import com.springbootjpa.global.NoSuchEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("이", "근우");
    }

    @Test
    void 고객을_저장한다() {
        // given
        Name name = new Name("이", "근우");

        // when
        Customer result = customerRepository.save(customer);

        // then
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    void 고객을_아이디로_조회한다() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();

        // when
        Customer result = customerRepository.getById(id);

        // then
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void 고객의_이름을_갱신한다() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        Name name = new Name("김", "근우");
        Long id = savedCustomer.getId();

        // when
        savedCustomer.changeName("김", "근우");
        Customer result = customerRepository.getById(id);

        // then
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    void 고객을_아이디로_삭제한다() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        Long id = savedCustomer.getId();

        // when
        customerRepository.deleteById(id);

        // then
        assertThatThrownBy(() -> customerRepository.getById(id))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessage("존재하지 않는 고객입니다.");
    }
}
