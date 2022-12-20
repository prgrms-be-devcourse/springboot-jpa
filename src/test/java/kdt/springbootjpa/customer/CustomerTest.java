package kdt.springbootjpa.customer;

import kdt.springbootjpa.customer.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

public class CustomerTest {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Test
    @DisplayName("[성공] Customer 생성하기")
    void testCreateCustomer() {
        String firstName = "sinyoung";
        String lastName = "bok";
        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("[실패] Customer 생성 시, 성/이름이 20자가 넘는 경우")
    void testOverSize() {
        String firstName = "20자가넘는이름입니다20자가넘는이름입니다20자가넘는이름입니다";
        String lastName = "bok";
        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(1);
        assertThat(msgs).contains("20자 이내로 입력해주세요");
    }

    @Test
    @DisplayName("[실패] Customer 생성 시, 성/이름에 null이 들어간 경우")
    void testNull() {
        String firstName = null;
        String lastName = "bok";
        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(1);
        assertThat(msgs).contains("공백일 수 없습니다");
    }

    @Test
    @DisplayName("[실패] Customer 생성 시, 성/이름이 공백인 경우")
    void testBlank() {
        String firstName = " ";
        String lastName = "";
        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).build();

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();

        assertThat(msgs).hasSize(2);
        assertThat(msgs).contains("공백일 수 없습니다");
    }
}
