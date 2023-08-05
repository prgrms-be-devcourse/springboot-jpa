package com.programmers.jpa.order.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    protected Order() {
    }

    public Order(OrderStatus orderStatus, String memo) {
        validateMemo(memo);
        validateOrderStatus(orderStatus);
        this.orderStatus = orderStatus;
        this.memo = memo;
    }

    void changeOrderStatus(OrderStatus orderStatus) {
        validateOrderStatus(orderStatus);
        this.orderStatus = orderStatus;
    }

    private static void validateOrderStatus(OrderStatus orderStatus) {
        if (Objects.isNull(orderStatus)) {
            throw new IllegalArgumentException("주문 상태가 없습니다.");
        }
    }

    void changeMemo(String memo) {
        validateMemo(memo);
        this.memo = memo;
    }

    private static void validateMemo(String memo) {
        if (Objects.isNull(memo)) {
            throw new IllegalArgumentException("메모가 없습니다.");
        }
    }
}
