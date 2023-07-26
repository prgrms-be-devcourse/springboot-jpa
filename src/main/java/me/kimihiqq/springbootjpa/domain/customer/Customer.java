package me.kimihiqq.springbootjpa.domain.customer;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimihiqq.springbootjpa.exception.InvalidNameException;
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Builder
    public Customer(String firstName, String lastName) {
        validateName(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void updateName(String newFirstName, String newLastName) {
        validateName(newFirstName, newLastName);
        this.firstName = newFirstName;
        this.lastName = newLastName;
    }

    private void validateName(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidNameException("이름은 비어 있거나 NULL 일 수 없습니다.");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidNameException("성은 비어 있거나 NULL 일 수 없습니다.");
        }
    }
}
