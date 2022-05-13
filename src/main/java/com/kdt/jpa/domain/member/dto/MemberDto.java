package com.kdt.jpa.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private String nickName;
    private int age;
    private String address;
    private String description;

}
