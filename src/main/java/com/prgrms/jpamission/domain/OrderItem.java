package com.prgrms.jpamission.domain;

import com.prgrms.jpamission.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 10)
    private int price;

    @Column(nullable = false, length = 10)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem")
    private List<Item> items = new ArrayList<>();

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)){
            this.order.getOrderItems().remove(this);
        }

        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item) {
        item.setOrderItem(this);
    }

}