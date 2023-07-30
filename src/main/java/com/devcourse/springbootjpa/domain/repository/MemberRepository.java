package com.devcourse.springbootjpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootjpa.domain.order.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
