package com.programmers.jpa.order.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import com.programmers.jpa.order.exception.OrderStatusException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    public Order(String memo) {
        validateMemo(memo);
        this.orderStatus = OrderStatus.SUCCESS;
        this.memo = memo;
    }

    public void changeOrderStatusToDelivering() {
        if (this.orderStatus == OrderStatus.DELIVERY_COMPLETE
                || this.orderStatus == OrderStatus.DELIVERING) {
            throw new OrderStatusException("현재 주문 상태가 성공이 아닙니다.");
        }
        this.orderStatus = OrderStatus.DELIVERING;
    }

    public void changeOrderStatusToDeliveryComplete() {
        if (this.orderStatus == OrderStatus.SUCCESS
                || this.orderStatus == OrderStatus.DELIVERY_COMPLETE) {
            throw new OrderStatusException("현재 주문 상태가 배달중이 아닙니다.");
        }
        this.orderStatus = OrderStatus.DELIVERY_COMPLETE;
    }

    public void changeMemo(String memo) {
        validateMemo(memo);
        this.memo = memo;
    }

    private static void validateMemo(String memo) {
        if (Objects.isNull(memo) || memo.isBlank()) {
            throw new IllegalArgumentException("메모가 없습니다.");
        }
    }
}
