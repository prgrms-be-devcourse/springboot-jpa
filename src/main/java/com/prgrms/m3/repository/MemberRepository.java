package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
