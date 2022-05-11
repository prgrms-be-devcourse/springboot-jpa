package devcoursejpa.jpa.domain;

import java.util.Objects;

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
        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last Name 은 공백일 수 없습니다.");
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName.isBlank()) {
            throw new IllegalArgumentException("First Name 은 공백일 수 없습니다.");
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