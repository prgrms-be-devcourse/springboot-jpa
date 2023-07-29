package programmers.jpaWeekly.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.jpaWeekly.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
