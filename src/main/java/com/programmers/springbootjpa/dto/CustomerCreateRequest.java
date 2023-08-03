package com.programmers.springbootjpa.dto;

import com.programmers.springbootjpa.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {

    private String name;
    private Integer age;
    private String nickName;
    private String address;

    public Customer toEntity() {
        return Customer.builder()
                .name(name)
                .age(age)
                .nickName(nickName)
                .address(address)
                .build();
    }
}
