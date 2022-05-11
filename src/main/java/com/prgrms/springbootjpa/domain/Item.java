package com.prgrms.springbootjpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {
  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_item_id", referencedColumnName = "id")
  private OrderItem orderItem;
}
