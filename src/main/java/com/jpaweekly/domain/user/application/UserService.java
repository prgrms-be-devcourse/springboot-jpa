package com.jpaweekly.domain.user.application;

import com.jpaweekly.domain.user.dto.UserCreateRequest;

public interface UserService {
    Long createUser(UserCreateRequest request);
}
