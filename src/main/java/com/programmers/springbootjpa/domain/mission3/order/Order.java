package com.programmers.springbootjpa.domain.mission3.order;

import com.programmers.springbootjpa.domain.mission3.BaseEntity;
import com.programmers.springbootjpa.domain.mission3.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public Order(OrderStatus orderStatus, String memo, Member member) {
        this.orderDatetime = LocalDateTime.now();
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
    }

    public void update(OrderStatus orderStatus, String memo, Member member) {
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
    }
}
