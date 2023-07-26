package com.programmers.jpa.order.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
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
