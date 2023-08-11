package com.example.springjpamission.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class Name {

    private String firstName;

    private String lastName;

    protected Name() { }

    public Name(String firstName, String lastName) {
        validateName(firstName,lastName );
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validateName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException();
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
