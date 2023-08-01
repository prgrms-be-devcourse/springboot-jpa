package prgrms.lecture.jpa.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
@Getter
public class Customer {

    private static final String ONLY_CHARACTER = "^[^\\d\\s!@#$%^&*(),.?\":{}|<>]+$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "이름을 입력하세요.")
    @Pattern(regexp = ONLY_CHARACTER, message = "이름은 문자로 구성.")
    private String firstName;
    @NotBlank(message = "성을 입력하세요.")
    @Pattern(regexp = ONLY_CHARACTER, message = "성은 문자로 구성.")
    private String lastName;

    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(String firstName, String lastName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
        if (lastName != null) {
            this.lastName = lastName;
        }
    }
}
