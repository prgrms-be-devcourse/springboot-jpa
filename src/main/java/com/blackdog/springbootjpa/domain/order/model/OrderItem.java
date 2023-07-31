package com.blackdog.springbootjpa.domain.order.model;

import com.blackdog.springbootjpa.domain.item.model.Item;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_item")
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    private int count;

    @Builder
    public OrderItem(Order order, Item item, int count) {
        this.order = order;
        this.item = item;
        this.count = count;
    }

}
