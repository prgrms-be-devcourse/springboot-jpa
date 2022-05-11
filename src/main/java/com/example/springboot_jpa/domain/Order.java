package com.example.springboot_jpa.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
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

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  public void setMember(Member member) {
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
