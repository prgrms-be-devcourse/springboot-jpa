package com.example.kdt.spring.jpa.domain.order;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_datetime")
    @NotNull
    private LocalDateTime orderDatetime;

    @Column(name = "memo")
    @Nullable
    private String memo;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @NotNull
    private Member member;

    @OneToMany(mappedBy = "order")
    @NotEmpty
    private List<OrderItem> orderItems = new ArrayList<>();

    public void attachMember(Member member) {
        if(Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    @Builder
    public Order(LocalDateTime orderDatetime, String memo, OrderStatus orderStatus) {
        this.orderDatetime = orderDatetime;
        this.memo = memo;
        this.orderStatus = orderStatus;
    }
}
