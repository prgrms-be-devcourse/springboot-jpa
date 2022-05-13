package com.example.springbootjpa.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // 오더추가
    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItemList().remove(this);
        }
        this.order = order;
        order.getOrderItemList().add(this);
    }


    // item추가
    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderItemList().remove(this);
        }
        this.item = item;
        item.getOrderItemList().add(this);
    }


}
