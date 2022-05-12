package org.prgrms.springjpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    //fk
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "item_id")
    private Long itemId;
}
