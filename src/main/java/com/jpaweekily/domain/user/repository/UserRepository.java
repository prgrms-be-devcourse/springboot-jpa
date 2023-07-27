package com.jpaweekily.domain.user.repository;

import com.jpaweekily.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);
}
