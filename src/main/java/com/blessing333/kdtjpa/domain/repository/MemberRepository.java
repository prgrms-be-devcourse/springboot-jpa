package com.blessing333.kdtjpa.domain.repository;

import com.blessing333.kdtjpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
