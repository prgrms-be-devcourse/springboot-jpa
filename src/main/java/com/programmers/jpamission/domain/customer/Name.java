package com.programmers.jpamission.domain.customer;

import com.programmers.jpamission.global.exception.ErrorMessage;
import com.programmers.jpamission.global.exception.InvalidNameRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND_FIRST = 20;
    private static final int UPPER_BOUND_LAST = 10;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    private Name(String firstName, String lastName) {
        checkNameLength(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Name of(String firstName, String lastName) {
        return new Name(firstName, lastName);
    }

    private void checkNameLength(String firstName, String lastName) {
        if (firstName == null || firstName.length() < LOWER_BOUND || firstName.length() > UPPER_BOUND_FIRST) {
            throw new InvalidNameRequest(ErrorMessage.INVALID_FIRST_NAME_REQUEST);
        }

        if (lastName == null || lastName.length() < LOWER_BOUND || lastName.length() > UPPER_BOUND_LAST) {
            throw new InvalidNameRequest(ErrorMessage.INVALID_LAST_NAME_REQUEST);
        }
    }
}
