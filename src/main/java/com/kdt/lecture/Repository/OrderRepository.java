package com.kdt.lecture.Repository;

import com.kdt.lecture.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {


}
