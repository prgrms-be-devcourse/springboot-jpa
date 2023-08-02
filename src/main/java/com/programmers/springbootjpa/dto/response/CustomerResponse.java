package com.programmers.springbootjpa.dto.response;

import com.programmers.springbootjpa.domain.Address;
import com.programmers.springbootjpa.domain.Customer;
import lombok.Getter;

@Getter
public class CustomerResponse {

    private final Long id;
    private final String name;
    private final Integer age;
    private final String nickName;
    private final Address address;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.age = customer.getAge();
        this.nickName = customer.getNickName();
        this.address = customer.getAddress();
    }
}
