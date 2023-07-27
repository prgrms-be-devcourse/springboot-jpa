package com.kdt.mission1.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;
    @Column(name = "memo", nullable = false)
    private String memo;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_datetime", nullable = false)
    private LocalDateTime orderDateTime;


}
