package com.kdt.kdtjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("이름이 공백일 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "          "})
    void validateBlank(String name) {
        // Given, When, Then
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("값이 같으면 Name 객체의 동등성을 보장한다.")
    @Test
    void when_nameValueIsSame_Expects_SameObject() {
        // Given, When
        Name name1 = new Name("Hwang");
        Name name2 = new Name("Hwang");

        // Then
        assertThat(name1).isEqualTo(name2);
        assertThat(name1).hasSameHashCodeAs(name2);
    }

    @DisplayName("값이 같으면 Name 객체의 동등성을 보장하지 않는다.")
    @Test
    void when_nameValueIsNotSame_Expects_DifferentObject() {
        // Given, When
        Name name1 = new Name("Hwang");
        Name name2 = new Name("Kim");

        // Then
        assertThat(name1).isNotEqualTo(name2);
        assertThat(name1).doesNotHaveSameHashCodeAs(name2);
    }
}
