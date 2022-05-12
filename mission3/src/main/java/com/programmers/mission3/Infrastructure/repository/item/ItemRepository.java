package com.programmers.mission3.Infrastructure.repository;

import com.programmers.mission3.Infrastructure.domain.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long>{
}
