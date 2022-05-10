package com.example.springjpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;
    @Column(name = "memo")
    private String memo;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    // member_fk
    @Column(name = "member_id")
    private Long memberId;
}
