package com.example.springboot_jpa.domain.item;

import com.example.springboot_jpa.domain.orderItem.OrderItem;
import com.example.springboot_jpa.global.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem); // 이 orderItem은 내꺼야
        if (orderItem.getItem() != null) { //근데 orderItem이 주인이 있어
            orderItem.setItem(this); //바꿔
        }
    }

    // 기본 생성자
    public Item() {
    }

    // AllArgus


    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
