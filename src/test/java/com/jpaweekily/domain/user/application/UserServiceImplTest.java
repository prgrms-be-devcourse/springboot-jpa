package com.jpaweekily.domain.user.application;

import com.jpaweekily.domain.user.dto.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Test
    void create_user_test() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("test", "123", "tester");

        Long userId = userService.createUser(userCreateRequest);

        assertThat(userId).isNotNull();
    }

}