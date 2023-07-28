package com.springbootjpa.domain;

import com.springbootjpa.global.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findById(Long id);

    default Item getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("존재하지 않는 상품입니다."));
    }
}
