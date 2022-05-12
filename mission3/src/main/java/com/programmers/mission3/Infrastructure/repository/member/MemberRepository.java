package com.programmers.mission3.Infrastructure.repository;

import com.programmers.mission3.Infrastructure.domain.order.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
