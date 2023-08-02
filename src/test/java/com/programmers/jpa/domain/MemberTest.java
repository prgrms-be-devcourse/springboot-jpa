package com.programmers.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class MemberTest {
    @Nested
    @DisplayName("중첩: member 생성")
    class NewMemberTest {
        @Test
        @DisplayName("예외: null로 주어진 first name")
        void newMember_ButNullFirstName() {
            //given
            String nullFirstName = null;

            //when
            //then
            assertThatThrownBy(() -> new Member(nullFirstName, "name"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("예외: null로 주어진 last name")
        void newMember_ButNullLastName() {
            //given
            String nullLastName = null;

            //when
            //then
            assertThatThrownBy(() -> new Member("name", nullLastName))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "!@#$$", "12345", "abc12345", "abc!@#$%"
        })
        @DisplayName("예외: 잘못된 형식의 first name")
        void newMember_ButInvalidFirstName(String invalidFirstName) {
            assertThatThrownBy(() -> new Member(invalidFirstName, "name"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "!@#$$", "12345", "abc12345", "abc!@#$%"
        })
        @DisplayName("예외: 잘못된 형식의 last name")
        void newMember_ButInvalidLastName(String invalidLastName) {
            assertThatThrownBy(() -> new Member("name", invalidLastName))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("중첩: member 업데이트")
    class UpdateMemberTest {
        Member givenMember;

        @BeforeEach
        void setUp() {
            givenMember = new Member("name", "name");
        }

        @Test
        @DisplayName("성공")
        void updateMember() {
            //given
            String updateFistName = "updateFirstName";
            String updateLastName = "updateLastName";

            //when
            givenMember.updateMember(updateFistName, updateLastName);

            //then
            assertThat(givenMember.getFirstName()).isEqualTo(updateFistName);
            assertThat(givenMember.getLastName()).isEqualTo(updateLastName);
        }

        @ParameterizedTest
        @CsvSource({
                "!@#$$", "12345", "abc12345", "abc!@#$%"
        })
        @DisplayName("예외: 잘못된 형식의 first name")
        void updateMember_ButInvalidFirstName(String invalidFirstName) {
            assertThatThrownBy(() -> givenMember.updateMember(invalidFirstName, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @CsvSource({
                "!@#$$", "12345", "abc12345", "abc!@#$%"
        })
        @DisplayName("예외: 잘못된 형식의 last name")
        void updateMember_ButInvalidLastName(String invalidLastName) {
            assertThatThrownBy(() -> givenMember.updateMember(null, invalidLastName))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}