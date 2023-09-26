package com.example.weeklyjpa.repository;

import com.example.weeklyjpa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
