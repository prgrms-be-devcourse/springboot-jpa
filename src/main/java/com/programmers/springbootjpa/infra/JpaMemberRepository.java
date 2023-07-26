package com.programmers.springbootjpa.infra;

import com.programmers.springbootjpa.domain.Member;
import com.programmers.springbootjpa.domain.MemberRepository;
import com.programmers.springbootjpa.ui.dto.MemberSaveRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager entityManager;

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Member save(Member member) {
        entityManager.persist(member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Member member = entityManager.find(Member.class, id);

        if (member == null) {
            throw new EntityNotFoundException("id do not exists");
        }

        entityManager.remove(member);
    }
}
