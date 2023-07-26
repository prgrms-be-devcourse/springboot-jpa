package com.programmers.springbootjpa.dto;

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
}
