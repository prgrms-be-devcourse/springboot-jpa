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

    public Customer(Long id, String firstName, String lastName) {
        validateName(firstName, lastName);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected Customer() {
    }

    private void validateName(String firstName, String lastName) {
        checkEmptyName(firstName, lastName);
        checkIllegalCharacter(firstName, lastName);
    }

    private void checkEmptyName(String firstName, String lastName) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName)) {
            throw new IllegalNameException("[ERROR] 이름 값은 비어있을 수 없습니다!");
        }
    }

    private void checkIllegalCharacter(String firstName, String lastName) {
        String pattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";

        if (!Pattern.matches(pattern, firstName) ||
                !Pattern.matches(pattern, lastName)) {
            throw new IllegalNameException("[ERROR] 이름 값은 한글과 영문만 가능합니다!");
        }
    }
}
