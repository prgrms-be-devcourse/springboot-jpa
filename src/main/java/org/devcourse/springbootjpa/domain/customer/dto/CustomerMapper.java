package org.devcourse.springbootjpa.domain.customer.dto;

import org.devcourse.springbootjpa.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer fromInsertDto(CustomerInsertDto customerInsertDto) {
        return Customer.createWithNames(customerInsertDto.firstName(),
                customerInsertDto.lastName());
    }

    public Customer fromUpdateDto(CustomerUpdateDto customerUpdateDto) {
        return Customer.create(customerUpdateDto.id(),
                customerUpdateDto.firstName(),
                customerUpdateDto.lastName());
    }

    public CustomerInsertDto toInsertDto(Customer customer) {
        return new CustomerInsertDto(customer.getFirstName(), customer.getLastName());
    }

    public CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(customer.getFirstName(), customer.getLastName());
    }

    public CustomerInsertDto insertRequestDtoToInsertDto(CustomerInsertRequestDto insertRequestDto) {
        return new CustomerInsertDto(insertRequestDto.firstName(), insertRequestDto.lastName());
    }

    public CustomerInsertDto updateRequestDtoToInsertDto(CustomerUpdateRequestDto updateRequestDto) {
        return new CustomerInsertDto(updateRequestDto.firstName(), updateRequestDto.lastName());
    }
}
