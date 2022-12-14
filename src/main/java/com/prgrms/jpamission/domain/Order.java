package com.prgrms.jpamission.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String memo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "order_status")
    @ColumnDefault("OPENED")
    private OrderStatus orderStatus;

    @Column(columnDefinition = "TIMESTAMP", name = "order_datetime", nullable = false)
    private LocalDateTime orderDateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}