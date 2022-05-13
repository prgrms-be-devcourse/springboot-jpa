package com.prgrms.springJpa.domain.order;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    protected OrderItem() {
    }

    public OrderItem(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.removeOrderItem(this);
        }
        this.order = order;
    }

    public void changeItem(Item item) {
        if (Objects.nonNull(this.item)) {
            this.item.removeOrderItem(this);
        }
        this.item = item;
    }
}
