package com.example.jpaweekly.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @Column(name = "id")
  private String uuid;

  @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
  private LocalDateTime orderDatetime;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Lob
  private String memo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", referencedColumnName = "id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Builder
  public Order(String memo, Member member) {
    this.uuid = String.valueOf(UUID.randomUUID());
    this.orderDatetime = LocalDateTime.now();
    this.orderStatus = OrderStatus.OPENED;
    this.memo = memo;
    this.member = member;
  }

  public void addMember(Member member) {
    if (Objects.nonNull(this.member)) {
      this.member.getOrders().remove(this);
    }

    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItem.setOrder(this);
  }
}
