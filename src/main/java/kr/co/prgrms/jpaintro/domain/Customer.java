package kr.co.prgrms.jpaintro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.prgrms.jpaintro.exception.IllegalNameException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Getter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Long id, String firstName, String lastName) {
        validateName(firstName);
        validateName(lastName);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName;
    }

    public void changeLastName(String lastName) {
        validateName(lastName);
        this.lastName = lastName;
    }

    private void validateName(String name) {
        checkEmptyName(name);
        checkIllegalCharacter(name);
    }

    private void checkEmptyName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalNameException("[ERROR] 이름 값은 비어있을 수 없습니다!");
        }
    }

    private void checkIllegalCharacter(String name) {
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";

        if (!Pattern.matches(pattern, name)) {
            throw new IllegalNameException("[ERROR] 이름 값은 한글과 영문만 가능합니다!");
        }
    }
}
