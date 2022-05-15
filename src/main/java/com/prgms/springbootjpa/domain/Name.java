package com.prgms.springbootjpa.domain;

import static com.prgms.springbootjpa.exception.ExceptionMessage.FIRST_NAME_FORMAT_EXP_MSG;
import static com.prgms.springbootjpa.exception.ExceptionMessage.LAST_NAME_FORMAT_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Name {

    private static final int FIRST_NAME_LEN_MIN = 1;
    private static final int FIRST_NAME_LEN_MAX = 10;
    private static final int LAST_NAME_LEN_MIN = 1;
    private static final int LAST_NAME_LEN_MAX = 5;

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
        if (lastName.length() < LAST_NAME_LEN_MIN || lastName.length() > LAST_NAME_LEN_MAX) {
            throw new InvalidNameLengthException(LAST_NAME_FORMAT_EXP_MSG);
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName.length() < FIRST_NAME_LEN_MIN || firstName.length() > FIRST_NAME_LEN_MAX) {
            throw new InvalidNameLengthException(FIRST_NAME_FORMAT_EXP_MSG);
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
