package com.kdt.jpa.domain.item;

import com.kdt.jpa.domain.BaseEntity;
import com.kdt.jpa.domain.order.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "item")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int stockQuantity;

    @ManyToOne()
    @JoinColumn(name = "order_item", referencedColumnName = "id")
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if(Objects.nonNull(this.orderItem)) {
            this.orderItem.getItems().remove(this);
        }
        this.orderItem = orderItem;
        orderItem.getItems().add(this);
    }


}
