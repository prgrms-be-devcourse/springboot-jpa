package com.programmers.jpapractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpapractice.domain.order.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
