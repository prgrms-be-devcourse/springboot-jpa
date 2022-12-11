package com.programmers.jpa.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private String firstName;

    private String lastName;

    public Name() {}

    public Name(String firstName, String lastName) {
        validate(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validate(String firstName, String lastName) {
        if (firstName.equals("") || lastName.equals(""))
            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }
}
