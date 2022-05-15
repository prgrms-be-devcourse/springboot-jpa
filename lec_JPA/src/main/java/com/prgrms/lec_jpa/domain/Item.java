package com.prgrms.lec_jpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long price;
    private long stockQuantity;

    protected Item() {

    }

    private Item(ItemBuilder builder) {

        super(LocalDateTime.now(), builder.createdBy);
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
    }

    public Long getId() {

        return id;
    }

    public long getPrice() {

        return price;
    }

    public long getStockQuantity() {

        return stockQuantity;
    }

    public static class ItemBuilder {

        private long price;
        private long stockQuantity;
        private String createdBy;

        public ItemBuilder price(long value) {

            this.price = value;

            return this;
        }

        public ItemBuilder stockQuantity(long value) {

            this.stockQuantity = value;

            return this;
        }

        public ItemBuilder createdBy(String value) {

            this.createdBy = value;

            return this;
        }

        public Item build() {

            return new Item(this);
        }
    }

    public static ItemBuilder builder() {

        return new ItemBuilder();
    }
}
