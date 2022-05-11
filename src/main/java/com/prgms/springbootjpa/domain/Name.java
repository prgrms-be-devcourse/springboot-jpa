package com.prgms.springbootjpa.domain;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NAME_FORMAT_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Name {

    private String firstName;
    private String lastName;

    protected Name() {
    }

    public Name(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validateLastName(String lastName) {
        if (lastName.length() < 1 || lastName.length() > 10) {
            throw new InvalidNameLengthException(NAME_FORMAT_EXP_MSG);
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName.length() < 1 || firstName.length() > 10) {
            throw new InvalidNameLengthException(NAME_FORMAT_EXP_MSG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return firstName.equals(name.firstName) && lastName.equals(name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
