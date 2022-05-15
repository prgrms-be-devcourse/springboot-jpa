package com.prgrms.lec_jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long price;
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    protected OrderItem() {

    }

    private OrderItem(OrderItemBuilder builder) {

        super(LocalDateTime.now(), builder.createdBy);
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public void setOrder(Order order) {

        this.order = order;
    }

    public void setItem(Item item) {

        this.item = item;
    }

    public Long getId() {

        return id;
    }

    public long getPrice() {

        return price;
    }

    public long getQuantity() {

        return quantity;
    }

    public Order getOrder() {

        return order;
    }

    public Item getItem() {
        return item;
    }

    public static class OrderItemBuilder {

        private long price;
        private long quantity;
        private String createdBy;

        public OrderItemBuilder price(long value) {

            this.price = value;

            return this;
        }

        public OrderItemBuilder quantity(long value) {

            this.quantity = value;

            return this;
        }

        public OrderItemBuilder createdBy(String value) {

            this.createdBy = value;

            return this;
        }

        public OrderItem build() {

            return new OrderItem(this);
        }
    }

    public static OrderItemBuilder builder() {

        return new OrderItemBuilder();
    }
}
