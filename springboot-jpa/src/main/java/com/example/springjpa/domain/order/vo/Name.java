package com.example.springjpa.domain.order.vo;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    private static final String NOT_NULL_ERR_MSG = "이름은 공백이 될 수 없습니다.";
    private static final String NOT_EMPTY_ERR_MSG = "이름은 빈 문자열 될 수 없습니다.";
    @Column
    private String firstName;
    @Column
    private String lastName;

    protected Name() {
    }

    public Name(String firstName, String lastName) {
        validate(firstName);
        validate(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void validate(String name) {
        Assert.notNull(name, NOT_NULL_ERR_MSG);
        Assert.isTrue(!name.isEmpty(), NOT_EMPTY_ERR_MSG);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
