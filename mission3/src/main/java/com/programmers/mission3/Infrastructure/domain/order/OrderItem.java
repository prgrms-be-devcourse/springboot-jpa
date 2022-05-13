package com.programmers.mission3.Infrastructure.domain.order;

import com.programmers.mission3.Infrastructure.domain.common.BaseEntity;
import com.programmers.mission3.Infrastructure.domain.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Item> items;

    public void setOrder(Order order){
        if(Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item){
        item.setOrderItem(this);
    }

    @Builder
    public OrderItem(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
        items = new ArrayList<>();
    }
}
