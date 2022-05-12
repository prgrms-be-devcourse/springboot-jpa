package com.programmers.mission3.Infrastructure.repository.member;

import com.programmers.mission3.Infrastructure.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
