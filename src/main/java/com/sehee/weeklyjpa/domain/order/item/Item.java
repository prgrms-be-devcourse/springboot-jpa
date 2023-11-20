package com.sehee.weeklyjpa.domain.order.item;

import com.sehee.weeklyjpa.domain.BaseEntity;
import com.sehee.weeklyjpa.domain.order.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setItem(this);
    }
}
