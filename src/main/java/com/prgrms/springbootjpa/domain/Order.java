package com.prgrms.springbootjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {
  @Id
  @Column(name = "id")
  private Long id;
}
