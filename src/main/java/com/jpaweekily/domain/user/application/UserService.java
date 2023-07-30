package com.jpaweekily.domain.user.application;

import com.jpaweekily.domain.user.dto.UserCreateRequest;

public interface UserService {
    Long createUser(UserCreateRequest request);
}
