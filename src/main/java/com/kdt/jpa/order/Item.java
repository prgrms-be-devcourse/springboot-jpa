package com.kdt.jpa.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "item")
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Item(int price, int stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item() {
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }
}