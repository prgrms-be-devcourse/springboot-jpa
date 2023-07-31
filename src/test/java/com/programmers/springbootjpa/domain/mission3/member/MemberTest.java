package com.programmers.springbootjpa.domain.mission3.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @DisplayName("회원을 생성한다")
    @CsvSource(value = {
            "hyemin : hymn : 26 : 서울특별시 성북구",
            "혜민 : hhhmmm : 27 : 경기도 광주시"
    }, delimiter = ':')
    @ParameterizedTest
    void testCreate(String name, String nickname, int age, String address) {
        //given
        //when
        Member member = new Member(name, nickname, age, address);

        //then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getAge()).isEqualTo(age);
        assertThat(member.getAddress()).isEqualTo(address);
    }

    @DisplayName("회원을 수정한다")
    @Test
    void testUpdate() {
        //given
        Member member = new Member("hyemin", "hymn", 26, "서울특별시 성북구");

        //when
        member.updateName("min");
        member.updateNickname("nick");

        //then
        assertThat(member.getName()).isEqualTo("min");
        assertThat(member.getNickname()).isEqualTo("nick");
    }

    @DisplayName("회원 생성 시 이름이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민1", "@@", "hy3min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testName(String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Member(name, "hymn", 26, "서울특별시 성북구"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 생성 시 닉네임이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민&", "@@", "hy**min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testNickname(String nickname) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Member("hyemin", nickname, 26, "서울특별시 성북구"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 생성 시 나이가 0세 미만이면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testAge(int age) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Member("hyemin", "nick", age, "서울특별시 성북구"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 수정 시 이름이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민1", "@@", "hy3min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testNameUpdate(String name) {
        //given
        Member member = new Member("hyemin", "hymn", 26, "서울특별시 성북구");

        //when
        //then
        assertThatThrownBy(() -> member.updateName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 수정 시 닉네임이 조건에 맞지 않으면 예외처리한다")
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbb", "혜민&", "@@", "hy**min", "/bbb"})
    @NullAndEmptySource
    @ParameterizedTest
    void testNicknameUpdate(String nickname) {
        //given
        Member member = new Member("hyemin", "hymn", 26, "서울특별시 성북구");

        //when
        //then
        assertThatThrownBy(() -> member.updateNickname(nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원 수정 시 나이가 0세 미만이면 예외처리한다")
    @ValueSource(strings = {"-1", "-4", "-9"})
    @ParameterizedTest
    void testAgeUpdate(int age) {
        //given
        Member member = new Member("hyemin", "hymn", 26, "서울특별시 성북구");

        //when
        //then
        assertThatThrownBy(() -> member.updateAge(age))
                .isInstanceOf(IllegalArgumentException.class);
    }
}