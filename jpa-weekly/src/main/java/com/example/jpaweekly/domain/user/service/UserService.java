package com.example.jpaweekly.domain.user.service;

import com.example.jpaweekly.domain.user.dto.UserCreateRequest;

public interface UserService {
    Long createUser(UserCreateRequest request);
}
