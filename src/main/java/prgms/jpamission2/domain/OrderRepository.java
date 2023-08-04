package prgms.jpamission2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import prgms.mission3.order.domain.Order;


public interface OrderRepository extends JpaRepository<Order,String> {
}
