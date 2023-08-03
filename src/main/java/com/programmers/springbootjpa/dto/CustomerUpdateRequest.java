package com.programmers.springbootjpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CustomerUpdateRequest {

    private String name;
    private Integer age;
    private String address;
}
