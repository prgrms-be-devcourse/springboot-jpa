package com.blackdog.springbootjpa.domain.order.model;

import com.blackdog.springbootjpa.domain.item.model.Item;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders_items")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer count;

    @Builder
    protected OrderItem(
            Order order,
            Item item,
            Integer count
    ) {
        validate(order, item, count);
        this.order = order;
        this.item = item;
        this.count = count;
    }

    private void validate(Order order, Item item, Integer count) {
        Assert.notNull(order, "Order 가 존재하지 않습니다.");
        Assert.notNull(item, "item 가 존재하지 않습니다.");
        Assert.notNull(count, "count 가 존재하지 않습니다.");
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
