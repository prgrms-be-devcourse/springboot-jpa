package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.order.Member;
import com.example.springbootjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
