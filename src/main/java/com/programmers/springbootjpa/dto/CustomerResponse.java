package com.programmers.springbootjpa.dto;

import com.programmers.springbootjpa.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerResponse {

    private Long id;
    private String name;
    private Integer age;
    private String nickName;
    private String address;

    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getAge(),
                customer.getNickName(),
                customer.getAddress()
        );
    }
}
