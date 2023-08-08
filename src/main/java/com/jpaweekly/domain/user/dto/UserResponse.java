package com.jpaweekly.domain.user.dto;

import com.jpaweekly.domain.user.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String loginId,
        String password,
        String nickname,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(),
                user.getLoginId(),
                user.getPassword(),
                user.getNickname(),
                user.getCreatedAt());
    }
}
