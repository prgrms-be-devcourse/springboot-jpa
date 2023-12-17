package org.devcourse.springbootjpa.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.devcourse.springbootjpa.domain.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int stockQuantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    public void setOrderItem(OrderItem orderItem) {
        if (Objects.nonNull(orderItem)) {
            orderItem.getItems().remove(this);
        }

        this.orderItem = orderItem;
        orderItem.getItems().add(this);
    }
}
