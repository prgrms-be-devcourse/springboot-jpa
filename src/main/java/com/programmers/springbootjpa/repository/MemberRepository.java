package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
