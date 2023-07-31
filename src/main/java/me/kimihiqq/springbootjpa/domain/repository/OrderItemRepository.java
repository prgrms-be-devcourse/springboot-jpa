package me.kimihiqq.springbootjpa.domain.repository;
import me.kimihiqq.springbootjpa.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
