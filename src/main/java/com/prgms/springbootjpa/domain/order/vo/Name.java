package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NAME_LENGTH_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {

    @Column(nullable = false, length = 30)
    private String name;

    protected Name() {
    }

    public Name(String name) {
        validateNameFormat(name);
        this.name = name;
    }

    private void validateNameFormat(String name) {
        if (name.length() < 1 || name.length() > 30) {
            throw new InvalidNameLengthException(NAME_LENGTH_EXP_MSG);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name1 = (Name) o;
        return getName().equals(name1.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
