package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.QUANTITY_RANGE_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidQuantityRangeException;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Quantity implements Comparable<Quantity> {

    private static final int MIN = 1;
    private static final int MAX = 1000;

    private int quantity;

    protected Quantity() {
    }

    public Quantity(int quantity) {
        validateQuantityRange(quantity);
        this.quantity = quantity;
    }

    private void validateQuantityRange(int quantity) {
        if (quantity < MIN || quantity > MAX) {
            throw new InvalidQuantityRangeException(QUANTITY_RANGE_EXP_MSG);
        }
    }

    public Quantity sub(Quantity quantity) {
        return new Quantity(this.quantity - quantity.getQuantity());
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    @Override
    public int compareTo(Quantity o) {
        return Integer.compare(quantity, o.quantity);
    }
}
