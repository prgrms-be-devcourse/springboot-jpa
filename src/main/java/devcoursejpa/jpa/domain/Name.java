package devcoursejpa.jpa.domain;

import java.util.Objects;

public class Name {

    private String firstName;
    private String lastName;

    protected Name() {

    }

    public Name(String firstName, String lastName) {
        validateName(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validateName(String firstName, String lastName) {
        if (lastName.isBlank() || firstName.isBlank()) {
            throw new IllegalArgumentException("First Name 혹은 Last Name 은 공백일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}