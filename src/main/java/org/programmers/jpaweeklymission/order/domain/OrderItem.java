package org.programmers.jpaweeklymission.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.jpaweeklymission.global.BaseEntity;
import org.programmers.jpaweeklymission.item.domain.Item;

import java.util.Objects;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    Item item;

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
