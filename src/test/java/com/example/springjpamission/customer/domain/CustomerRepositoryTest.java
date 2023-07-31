package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {

    static final String FIRST_NAME = "별";
    static final String LAST_NAME = "김";

    @Autowired
    CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setName(new Name(FIRST_NAME,LAST_NAME));

        testCustomer = customerRepository.save(customer);
    }

    @Test
    @DisplayName("저장한 고객의 정보 중 last name이 의도한 이름으로 저장되었는지 확인한다.")
    void create() {
        //given
        Customer customer = new Customer();
        customer.setName(new Name("영운", "윤"));

        //when
        Customer save = customerRepository.save(customer);

        //then
        assertThat(save.getName().getLastName()).isEqualTo("윤");
    }

    @Test
    @DisplayName("저장한 고객의 정보를 primary key 찾았을 때 올바른 정보로 저장되었는지 확인한다.")
    void findById() {
        //when
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        //then
        assertThat(savedCustomer.getName().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(savedCustomer.getName().getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    @DisplayName("저장한 고객의 정보를 primary key로 찾은 후 삭제하였을 때 제대로 삭제되었는지 사이즈를 확인한다.")
    void deleteById(){
        //given
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));
        Long savedId = savedCustomer.getId();

        //when
        customerRepository.deleteById(savedId);

        //then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("저장한 고객의 정보를 수정하였을 때 수정된 정보로 저장되었는지 확인한다.")
    void update(){
        //given
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        //when
        savedCustomer.setName(new Name("영운","윤"));

        //then
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));
        assertThat(updatedCustomer.getName().getFirstName()).isEqualTo("영운");
    }
}
