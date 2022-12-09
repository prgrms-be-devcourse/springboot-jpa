package org.prgrms.devcoursejpa.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 20)
    private String lastName;

    public Customer(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }


    public void changeLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    private void validateLastName(String lastName) {
        if (!StringUtils.hasText(lastName.trim())) {
            throw new IllegalArgumentException("성은 필수 입력사항");
        }
        if (!lastName.matches("\\D*")) {
            throw new IllegalArgumentException("이름에 숫자는 포함될 수 없습니다.");
        }
    }

    private void validateFirstName(String firstName) {
        if (!StringUtils.hasText(firstName.trim())) {
            throw new IllegalArgumentException("이름은 필수 입력사항");
        }
        if (!firstName.matches("\\D*")) {
            throw new IllegalArgumentException("이름에 숫자는 포함될 수 없습니다.");
        }
    }
}
