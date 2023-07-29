package programmers.jpaWeekly.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.jpaWeekly.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
