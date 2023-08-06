package com.example.jpaweekly.domain.user.repository;

import com.example.jpaweekly.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
