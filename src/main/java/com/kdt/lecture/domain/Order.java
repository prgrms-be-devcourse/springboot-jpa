package com.kdt.lecture.domain;

import static com.kdt.lecture.domain.OrderStatus.OPENED;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order {

  @Id
  @Column(name = "id", columnDefinition = "BINARY(16)")
  @GeneratedValue(generator = "uuid2")
  private UUID id;

  @Column(name = "order_datetime")
  private LocalDateTime orderDatetime;

  @NotNull
  @Enumerated(value = STRING)
  @Column(name = "order_status", length = 30)
  private OrderStatus orderStatus;

  @Lob
  @Column(name = "memo")
  private String memo;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id", referencedColumnName = "id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
  private final List<OrderItem> orderItems = new ArrayList<>();

  public Order(Member member) {
    this.orderDatetime = LocalDateTime.now();
    this.orderStatus = OPENED;
    this.member = member;
    member.addOrder(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    if(orderItems.contains(orderItem)) return;
    orderItems.add(orderItem);
  }

  public void removeOrderItem(OrderItem orderItem) {
    orderItems.remove(orderItem);
  }
}