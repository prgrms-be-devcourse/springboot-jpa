package com.programmers.mission3.Infrastructure.repository.item;

import com.programmers.mission3.Infrastructure.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long>{
}
