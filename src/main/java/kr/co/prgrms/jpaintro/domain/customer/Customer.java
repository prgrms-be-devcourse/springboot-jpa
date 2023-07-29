package kr.co.prgrms.jpaintro.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.prgrms.jpaintro.exception.IllegalNameException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;


@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    public Customer(String firstName, String lastName) {
        validateName(firstName);
        validateName(lastName);

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
