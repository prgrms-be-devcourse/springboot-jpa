package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private int price;
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;

    @Column(name = "item_id")
    private Long item_id;
}
