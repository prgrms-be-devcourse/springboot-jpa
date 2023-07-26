package me.kimihiqq.springbootjpa.domain.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimihiqq.springbootjpa.exception.InvalidNameException;

import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
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
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]+$";

        if (!Pattern.matches(pattern, firstName)) {
            throw new InvalidNameException("이름은 한글과 영문만 가능하며, 비어 있거나 NULL 일 수 없습니다.");
        }

        if (!Pattern.matches(pattern, lastName)) {
            throw new InvalidNameException("성은 한글과 영문만 가능하며, 비어 있거나 NULL 일 수 없습니다.");
        }
    }
}