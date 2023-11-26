package com.example.jpa;

import com.example.jpa.customer.CustomerResponseDto;
import com.example.jpa.customer.CustomerSaveRequestDto;
import com.example.jpa.customer.CustomerService;
import com.example.jpa.customer.CustomerUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("정상적으로 등록되는 경우의 테스트")
    @Rollback
    public void insertTest() throws Exception {

        //given
        CustomerSaveRequestDto requestDto = new CustomerSaveRequestDto("username", "nickname", "password", "name");

        //when
        Long newCustomerId = customerService.saveCustomer(requestDto);
        CustomerResponseDto customer = customerService.getCustomer(newCustomerId);

        //then
        assertThat(newCustomerId).isEqualTo(customer.customerId());
    }


    @Test
    @DisplayName("정상적으로 모든 데이터를 불러오는 경우의 테스트")
    public void getTest() throws Exception {

        //given
        CustomerSaveRequestDto requestDto = new CustomerSaveRequestDto("username", "nickname", "password", "name");

        //when
        Long newCustomerId = customerService.saveCustomer(requestDto);
        List<CustomerResponseDto> customers = customerService.getCustomers();

        //then
        assertThat(customers).hasSize(1);
    }


    @Test
    @DisplayName("정상적으로 모든 수정하는 경우의 테스트")
    public void updateTest() throws Exception {

        //given
        CustomerSaveRequestDto requestDto = new CustomerSaveRequestDto("username", "nickname", "password", "name");
        CustomerUpdateRequestDto updateRequestDto = new CustomerUpdateRequestDto("username update", "nickname update", "password update");

        //when
        Long newCustomerId = customerService.saveCustomer(requestDto);
        customerService.updateCustomer(updateRequestDto, newCustomerId);

        CustomerResponseDto customer = customerService.getCustomer(newCustomerId);
        //then
        assertThat(customer.nickname()).isEqualTo(updateRequestDto.nickname());
        assertThat(customer.username()).isEqualTo(updateRequestDto.username());

    }

    @Test
    @DisplayName("정상적으로 삭제하는 경우의 테스트")
    public void deleteTest() throws Exception {

        //given
        CustomerSaveRequestDto requestDto = new CustomerSaveRequestDto("username", "nickname", "password", "name");
        CustomerUpdateRequestDto updateRequestDto = new CustomerUpdateRequestDto("username update", "nickname update", "password update");

        //when
        Long newCustomerId = customerService.saveCustomer(requestDto);
        customerService.deleteCustomer(newCustomerId);

        assertThatThrownBy(() -> customerService.getCustomer(newCustomerId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 유저를 찾을 수 없습니다.");

    }

}