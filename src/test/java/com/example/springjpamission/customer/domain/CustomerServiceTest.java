package com.example.springjpamission.customer.domain;

import com.example.springjpamission.customer.service.CustomerService;
import com.example.springjpamission.customer.service.dto.CustomerResponse;
import com.example.springjpamission.customer.service.dto.CustomerResponses;
import com.example.springjpamission.customer.service.dto.SaveCustomerRequest;
import com.example.springjpamission.customer.service.dto.UpdateCustomerRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    Long setUpCustomerId;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer(new Name("영운", "윤"));
        customerRepository.save(customer);
        setUpCustomerId = customer.getId();
    }

    @Test
    @DisplayName("customer를 저장 후 조회하여 이름을 확인한다.")
    void saveCustomer() {
        //given
        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest("별", "김");

        //when
        CustomerResponse customerResponse = customerService.saveCustomer(saveCustomerRequest);

        //then
        Customer findCustomer = customerRepository.findById(customerResponse.id()).get();
        assertThat(findCustomer.getName().getFirstName()).isEqualTo("별");
        assertThat(findCustomer.getName().getLastName()).isEqualTo("김");
    }

    @Test
    @DisplayName("setUp()에서 넣은 데이터를 업데이트 했을 때 올바른 이름으로 업데이트 되었는지 확인한다.")
    void updateCustomer() {
        //given
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(setUpCustomerId,
                "별", "김");

        //when
        customerService.updateCustomer(updateCustomerRequest);

        //then
        Customer findCustomer = customerRepository.findById(setUpCustomerId).get();
        assertThat(findCustomer.getName().getFirstName()).isEqualTo("별");
        assertThat(findCustomer.getName().getLastName()).isEqualTo("김");
    }

    @Test
    @DisplayName("전체 데이터 조회시 setUp()에 넣은 데이터를 조회하여 사이즈가 1이 된다.")
    void findAll() {
        //when
        PageRequest pageSize = PageRequest.of(0, 10);
        CustomerResponses findCustomers = customerService.findAll(pageSize);

        //then
        assertThat(findCustomers.customerResponses().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("고객 아이디로 삭제되어 저장소 사이즈가 0이 된다.")
    void deleteById() {
        //when
        customerService.deleteById(setUpCustomerId);

        //then
        List<Customer> findCustomers = customerRepository.findAll();
        assertThat(findCustomers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 고객 아이디로 삭제 요청시 EntityNotFoundException가 발생한다.")
    void deleteByNotExistId() {
        //when
        Exception exception = catchException(() -> customerService.deleteById(new Random().nextLong()));

        //then
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
    }

}
