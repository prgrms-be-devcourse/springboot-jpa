package com.example.jpaweekly.domain.user.service;

import com.example.jpaweekly.domain.user.dto.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void createTest(){
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest("login","password","닉네임");

        // When
        Long userId = userService.createUser(userCreateRequest);

        // Then
        assertThat(userId).isNotNull();
    }
}
