package programmers.jpaWeekly.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.jpaWeekly.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
