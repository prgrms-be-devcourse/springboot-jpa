package com.jpaweekily.domain.user.application;

import com.jpaweekily.domain.user.User;
import com.jpaweekily.domain.user.dto.UserCreateRequest;
import com.jpaweekily.domain.user.infrastructrue.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserCreateRequest request) {
        User user = User.builder()
                .loginId(request.loginId())
                .password(request.password())
                .nickName(request.nickName())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return user.getId();
    }
}
