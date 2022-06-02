package com.prgrms.springbootjpa.domain.order;

import com.prgrms.springbootjpa.global.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberTest {
    @Nested
    class MemberFieldValidationTest {
        @Nested
        class NameValidation {
            @Test
            @DisplayName("잘못된 name 필드 입력에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameHasText() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member(null, "j", 25, "경기도");
                });
                assertThat(e.getFieldName(), is("Member.name"));
                assertThat(e.getReason(), is("please input name"));
            }

            @Test
            @DisplayName("잘못된 name 필드 길이에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNameLength() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "j", 25, "경기도");
                });
                assertThat(e.getFieldName(), is("Member.name"));
                assertThat(e.getReason(), is("1 <= name <= 30"));
            }
        }

        @Nested
        class NickNameValidation {
            @Test
            @DisplayName("잘못된 nickName 필드 입력에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNickNameHasText() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member("jerry",  null, 25, "경기도");
                });
                assertThat(e.getFieldName(), is("Member.nickName"));
                assertThat(e.getReason(), is("please input nickName"));
            }

            @Test
            @DisplayName("잘못된 nickName 필드 길이에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testNickNameLength() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member("jerry", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 25, "경기도");
                });
                assertThat(e.getFieldName(), is("Member.nickName"));
                assertThat(e.getReason(), is("1 <= nickName <= 30"));
            }
        }

        @Test
        void testAgeValidation() {
            WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                Member member = new Member("jerry",  "j", 0, "경기도", "");
            });
            assertThat(e.getFieldName(), is("Member.age"));
            assertThat(e.getReason(), is("1 <= age"));
        }

        @Nested
        class AddressValidation {
            @Test
            @DisplayName("잘못된 address 필드 입력에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testAddressHasText() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member("jerry",  "j", 25, null);
                });
                assertThat(e.getFieldName(), is("Member.address"));
                assertThat(e.getReason(), is("please input address"));
            }

            @Test
            @DisplayName("잘못된 address 필드 길이에 정상적으로 예외를 발생하는지에 대한 테스트")
            void testAddressLength() {
                WrongFieldException e = assertThrows(WrongFieldException.class, () -> {
                    Member member = new Member("jerry", "j", 25, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                });
                assertThat(e.getFieldName(), is("Member.address"));
                assertThat(e.getReason(), is("1 <= address <= 30"));

            }
        }
    }
}