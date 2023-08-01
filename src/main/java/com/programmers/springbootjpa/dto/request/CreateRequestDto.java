package com.programmers.springbootjpa.dto.request;

import com.programmers.springbootjpa.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRequestDto {

    private String name;
    private String nickName;
    private Integer age;
    private String address;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .address(address)
                .build();
    }
}
