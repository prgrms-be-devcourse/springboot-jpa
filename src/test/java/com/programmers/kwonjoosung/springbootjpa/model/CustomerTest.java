package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CustomerTest {

    @Test
    @DisplayName("Customer 생성 테스트")
    void createTest() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        //when
        Customer customer = new Customer(firstName, lastName);
        //then
        assertThat(customer.getFirstName()).isEqualTo(firstName);
        assertThat(customer.getLastName()).isEqualTo(lastName);
        log.info("customer : firstName {}, lastName {}", customer.getFirstName(), customer.getLastName());
    }

    @Test
    @DisplayName("Customer 수정 테스트")
    void updateTest() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        Customer customer = new Customer(firstName, lastName);
        log.info("customer : firstName {}, lastName {}", customer.getFirstName(), customer.getLastName());
        //when
        customer.changeFirstName("SUNGJOO");
        customer.changeLastName("KIM");
        //then
        assertThat(customer.getFirstName()).isEqualTo("SUNGJOO");
        assertThat(customer.getLastName()).isEqualTo("KIM");
        log.info("customer : firstName {}, lastName {}", customer.getFirstName(), customer.getLastName());
    }

    @Test
    @DisplayName("[실패] 이름에는 숫자가 들어갈 수 없다")
    void nameValidationTest() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        //when
        Customer customer = new Customer(firstName, lastName);
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            customer.changeFirstName("joosung2");
            customer.changeLastName("kwon2");
        });
    }

    @Test
    @DisplayName("[실패] 이름에는 null, 공백, space 이 입력 될 수 없다")
    void nameValidationTest2() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        //when
        Customer customer = new Customer(firstName, lastName);
        //then
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> customer.changeFirstName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName(" ")),
                () -> assertThrows(NullPointerException.class, () -> customer.changeLastName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName(" "))
        );
    }

    @Test
    @DisplayName("[실패] 이름은 20자를 넘을 수 없다")
    void nameValidationTest3() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        //when
        Customer customer = new Customer(firstName, lastName);
        //then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName("joosungjoosungjoosungjoosung")),
                () -> assertThrows(IllegalArgumentException.class, () -> customer.changeLastName("kwonkwonkwonkwonkwonkwonkwon"))
        );
    }

    @Test
    @DisplayName("[실패] 이름 규칙에 맞지 않으면 생성할 수 없다")
    void nameValidationTest4() {
        //given
        String firstName = "joosung";
        String lastName = "kwon";
        //when
        //then
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Customer(null, lastName)), // @NotBlank가 동작하는 것인지?
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer("", lastName)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(" ", lastName)),
                () -> assertThrows(NullPointerException.class, () -> new Customer(firstName, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, "")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, " ")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer("joosungjoosungjoosungjoosung", lastName)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, "kwonkwonkwonkwonkwonkwonkwon")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer("joosung2", lastName)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Customer(firstName, "kwon2"))
        );
    }

}