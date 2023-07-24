package kr.co.springbootjpaweeklymission.member.domain.repository;

import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
