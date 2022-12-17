package com.programmers.kwonjoosung.springbootjpa.repository;

import com.programmers.kwonjoosung.springbootjpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
