package com.example.springjpa.domain.order.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

    @Test
    @DisplayName("이름에는 널이 입력될 수 없다.")
    void nameNullTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Name(null, "hi")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Name("hi", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Name(null, null))
        );
    }

    @Test
    @DisplayName("이름에는 공백이 입력될 수 없다.")
    void nameEmptyTest() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Name("", "hi")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Name("hi", "")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Name("", ""))
        );
    }

    @Test
    @DisplayName("동일성 동등성 비교")
    void testEquals() {
        String firstName = "first";
        String lastName = "last";

        Name name1 = new Name(firstName, lastName);
        Name name2 = new Name(firstName, lastName);

        Map<Name, Boolean> map = new HashMap<>();
        map.put(name1, true);
        map.put(name2, true);
        assertAll(
                () -> assertThat(map).hasSize(1),
                () -> assertThat(name1.equals(name2)).isTrue(),
                () -> assertThat(name2.equals(name1)).isTrue()
        );
    }

}