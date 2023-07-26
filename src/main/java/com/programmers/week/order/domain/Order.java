package com.programmers.week.order.domain;

import com.programmers.week.base.BaseEntity;
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

  void changeOrderStatus(OrderStatus orderStatus, String memo) {
    validateOrderStatus(orderStatus);
    validateMemo(memo);
    this.orderStatus = orderStatus;
  }

  private static void validateOrderStatus(OrderStatus orderStatus) {
    if (Objects.isNull(orderStatus)) {
      throw new IllegalStateException(String.format("수정할 주문 상태가 올바르지 않습니다.   ==> %s ", orderStatus));
    }
  }

  void changeMemo(String memo) {
    validateMemo(memo);
    this.memo = memo;
  }

  private static void validateMemo(String memo) {
    if (Objects.isNull(memo)) {
      throw new IllegalStateException("메모가 없습니다.");
    }
  }

}
