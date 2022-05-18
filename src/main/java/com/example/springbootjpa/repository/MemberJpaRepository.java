package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.main.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository {

    private final EntityManager entityManager;

    public MemberJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        Member member = entityManager.find(Member.class, memberId);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Member matchedMember = entityManager.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.ofNullable(matchedMember);
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    @Override
    public int deleteById(Long memberId) {
        Query query = entityManager.createQuery("DELETE FROM Member m WHERE m.uuid = :memberId")
                .setParameter("memberId", memberId);

        return query.executeUpdate();
    }

}
