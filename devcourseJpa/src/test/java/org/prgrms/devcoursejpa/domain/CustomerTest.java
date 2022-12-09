package org.prgrms.devcoursejpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    @DisplayName("이름이 누락된 경우 생성에 실패한다.")
    void 회원_생성_실패() {
        String firstName = "Kim";

        assertThrows(IllegalArgumentException.class, () -> new Customer("", firstName));
        assertThrows(IllegalArgumentException.class, () -> new Customer(" ", firstName));
    }

    @Test
    @DisplayName("성이 누락된 경우 생성에 실패한다.")
    void 회원_생성_실패2() {
        String lastName = "Giseo";

        assertThrows(IllegalArgumentException.class, () -> new Customer(lastName, ""));
        assertThrows(IllegalArgumentException.class, () -> new Customer(lastName, " "));
    }

    @Test
    @DisplayName("성과 이름이 누락된 경우 생성에 실패한다.")
    void 회원_생성_실패3() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("", ""));
        assertThrows(IllegalArgumentException.class, () -> new Customer(" ", " "));
    }

    @Test
    @DisplayName("이름에 숫자가 포함된 경우 예외가 발생한다.")
    void 회원_생성_실패4() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("Giseo", "K1m"));
        assertThrows(IllegalArgumentException.class, () -> new Customer("G1seo", "Kim"));
    }



    @Test
    @DisplayName("성과 이름이 존재하면 생성 성공")
    void 회원_생성_성공() {
        String firstName = "Giseo";
        String lastName = "Kim";

        assertDoesNotThrow(() -> new Customer(firstName, lastName));
    }

    @Test
    @DisplayName("이름을 변경할 때 빈 값으로 변경하면 예외가 발생한다.")
    void 회원_이름변경_실패() {
        String firstName = "Giseo";
        String lastName = "Kim";

        Customer customer = new Customer(firstName, lastName);

        assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> customer.changeLastName(""));
    }

    @Test
    @DisplayName("이름을 변경할 때 변경 값에 숫자가 포함되면 예외가 발생한다.")
    void 회원_이름변경_실패2() {
        String firstName = "Giseo";
        String lastName = "Kim";

        Customer customer = new Customer(firstName, lastName);

        assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName("G1seo"));
        assertThrows(IllegalArgumentException.class, () -> customer.changeLastName("K1m"));
    }

    @Test
    @DisplayName("이름을 변경할 때 빈 값으로 변경하면 예외가 발생한다.")
    void 회원_이름변경_성공() {
        String firstName = "Giseo";
        String lastName = "Kim";

        Customer customer = new Customer(firstName, lastName);

        assertThrows(IllegalArgumentException.class, () -> customer.changeFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> customer.changeLastName(""));
    }
}
