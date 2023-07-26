package com.programmers.springbootjpa.domain;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    void delete(Long id);
}
