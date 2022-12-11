package com.prgms.springbootjpa.domain.order.item;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NO_AVAILABLE_QUANTITY_EXP_MSG;

import com.prgms.springbootjpa.domain.order.BaseEntity;
import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import com.prgms.springbootjpa.exception.NoAvailableQuantityException;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Price price;

    @Embedded
    private Quantity stockQuantity;

    protected Item() {
    }

    protected Item(Price price, Quantity stockQuantity) {
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getStockQuantity() {
        return stockQuantity;
    }


    public void consumeQuantity(Quantity quantity) {
        if (stockQuantity.compareTo(quantity) < 0) {
            throw new NoAvailableQuantityException(NO_AVAILABLE_QUANTITY_EXP_MSG);
        }

        this.stockQuantity = stockQuantity.sub(quantity);
    }
}
