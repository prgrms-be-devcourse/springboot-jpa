package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.PRICE_RANGE_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidPriceRangeException;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Price {

    private static final int MIN = 1000;
    private static final int MAX = 1000000;

    private int price;

    protected Price() {
    }

    public Price(int price) {
        validatePriceRange(price);
        this.price = price;
    }

    private void validatePriceRange(int price) {
        if (price < MIN || price > MAX) {
            throw new InvalidPriceRangeException(PRICE_RANGE_EXP_MSG);
        }
    }

    public int getPrice() {
        return price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price1 = (Price) o;
        return getPrice() == price1.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }
}
