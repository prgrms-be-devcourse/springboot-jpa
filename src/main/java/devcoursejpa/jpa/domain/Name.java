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
            throw new IllegalArgumentException("Last Name 은 공백일 수 없습니다.");
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