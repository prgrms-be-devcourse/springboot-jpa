package com.example.jpaweekly.domain.user.service;


import com.example.jpaweekly.domain.user.User;
import com.example.jpaweekly.domain.user.dto.UserCreateRequest;
import com.example.jpaweekly.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserCreateRequest request){
        User user = User.builder()
                .loginId(request.loginId())
                .password(request.password())
                .nickname(request.nickname())
                .build();

        userRepository.save(user);

        return user.getId();
    }
}
