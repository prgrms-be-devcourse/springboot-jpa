package com.kdt.lecture.Repository;

import com.kdt.lecture.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
