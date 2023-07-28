package prgms.mission3.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import prgms.mission3.order.domain.Order;


public interface OrderRepository extends JpaRepository<Order,String> {
}
