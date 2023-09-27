package com.programmers.jpa.order.dto;

import lombok.*;

@Builder
public record MemberDto(
        Long id,
        String name,
        String nickName,
        int age,
        String address,
        String description
) {
}
