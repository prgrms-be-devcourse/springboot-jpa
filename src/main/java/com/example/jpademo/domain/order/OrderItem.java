package com.example.jpademo.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @Column(name = "order_id", insertable = false, updatable = false)
    private String orderId;
    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        if (Objects.nonNull(this.item)) {
            item.getOrderItems().remove(this);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }

}
