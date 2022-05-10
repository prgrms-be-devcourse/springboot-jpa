package com.part4.jpa2.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @Column(name ="id")
    private String uuid;
    @Lob
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    private Long memberId;

    @Builder
    public Order(String uuid, String memo, OrderStatus orderStatus, LocalDateTime orderDatetime, Long memberId) {
        this.uuid = uuid;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDatetime = orderDatetime;
        this.memberId = memberId;
    }
}
