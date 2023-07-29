package me.kimihiqq.springbootjpa.domain.repository;

import me.kimihiqq.springbootjpa.domain.order.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
