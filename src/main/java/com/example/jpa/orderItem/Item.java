package com.example.jpa.orderItem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "item")
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private int price;
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if (Objects.nonNull(this.orderItem)) {
            this.orderItem.getItems().remove(this);
        }

        this.orderItem = orderItem;
        orderItem.getItems().add(this);
    }
}
