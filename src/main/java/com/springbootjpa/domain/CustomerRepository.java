package com.springbootjpa.domain;

import com.springbootjpa.global.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    default Customer getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("존재하지 않는 고객입니다."));
    }
}
