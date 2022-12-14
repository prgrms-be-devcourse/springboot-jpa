package com.prgrms.jpamission.domain.item;

import com.prgrms.jpamission.domain.BaseEntity;
import com.prgrms.jpamission.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
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
