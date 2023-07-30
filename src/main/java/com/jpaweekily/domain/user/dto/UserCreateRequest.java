package com.jpaweekily.domain.user.dto;

public record UserCreateRequest (
        String loginId,
        String password,
        String nickname
){
}
