package com.programmers.week.order.domain;

import com.programmers.week.base.BaseEntity;
import com.programmers.week.exception.Message;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Lob
  private String memo;

  public Order(OrderStatus orderStatus, String memo) {
    validateOrderStatus(orderStatus);
    validateMemo(memo);
    this.orderStatus = orderStatus;
    this.memo = memo;
  }

  private static void validateOrderStatus(OrderStatus orderStatus) {
    if (Objects.isNull(orderStatus)) {
      throw new IllegalStateException(Message.INCORRECT_ORDER_STATUS);
    }
  }

  private static void validateMemo(String memo) {
    if (Objects.isNull(memo)) {
      throw new IllegalStateException(Message.MEMO_IS_NULL);
    }
  }

}
