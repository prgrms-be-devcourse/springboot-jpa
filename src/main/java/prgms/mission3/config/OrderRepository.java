package prgms.mission3.config;

import org.springframework.data.jpa.repository.JpaRepository;
import prgms.mission3.config.domain.Order;

public interface OrderRepository extends JpaRepository<Order,String> {
}
