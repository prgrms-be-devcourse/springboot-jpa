package com.prgrms.springbootjpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;
}
