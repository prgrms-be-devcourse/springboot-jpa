package com.springbootjpa.domain;

import com.springbootjpa.exception.NoValidCustomerException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Embeddable
public class Name {

    private final static int MAX_LAST_LENGTH = 5;

    private String first;

    private String last;

    public Name(String first, String last) {
        validateLastName(last);
        this.first = first;
        this.last = last;
    }

    private void validateLastName(String last) {
        if (last.length() > MAX_LAST_LENGTH) {
            throw new NoValidCustomerException("성을 제외하고 이름은 5자를 초과할 수 없습니다.");
        }
    }
}
