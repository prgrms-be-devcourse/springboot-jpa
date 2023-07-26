package com.prgms.springbootjpa.mission1;

import com.prgms.springbootjpa.domain.Customer;
import com.prgms.springbootjpa.domain.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaIntroduce {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("create - customer 생성한다.")
    void 생성() {
        //given
        Customer customer = new Customer("길동", "홍");

        //when
        Customer saved = customerRepository.save(customer);

        //then
        Assertions.assertThat(saved)
            .isEqualTo(customer);
    }

    @Test
    @DisplayName("read - customer 조회한다.")
    void 조회() {
        //given
        Customer customer = new Customer("길동", "홍");
        Customer saved = customerRepository.save(customer);

        //when
        Customer selected = customerRepository.findById(saved.getId()).get();

        //then
        Assertions.assertThat(selected)
            .isEqualTo(customer);
    }

    @Test
    @DisplayName("update - customer을 수정한다.")
    void 수정() {
        //given
        Customer customer = new Customer("길동", "홍");
        Customer saved = customerRepository.save(customer);

        //when
        customer.upateName("무개", "아");
        Customer updated = customerRepository.findById(saved.getId()).get();

        //then
        Assertions.assertThat(updated)
            .isEqualTo(customer);
    }

    @Test
    @DisplayName("delete - customer를 삭제한다.")
    void 삭제() {
        //given
        Customer customer = new Customer("gildong", "hong");
        Customer saved = customerRepository.save(customer);

        //when
        customerRepository.delete(customer);

        //then
        boolean present = customerRepository.findById(saved.getId()).isPresent();
        Assertions.assertThat(present)
            .isFalse();
    }
}
