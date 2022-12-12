package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.order.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
