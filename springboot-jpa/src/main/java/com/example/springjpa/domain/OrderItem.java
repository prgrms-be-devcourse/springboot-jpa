package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="item_id", referencedColumnName = "id")
    private Item item;

    public void setOrder(Order order){
        if(Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        this.order.getOrderItems().add(this);
    }

}
