package com.programmers.springbootjpa.domain.mission3.member;

import com.programmers.springbootjpa.domain.mission3.BaseEntity;
import com.programmers.springbootjpa.global.exception.InvalidRequestValueException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member extends BaseEntity {

    private static final int MAXIMUM_LENGTH_LIMIT = 20;
    private static final int MINIMUM_AGE_LIMIT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    public Member(String name, String nickname, int age, String address) {
        checkName(name);
        checkNickname(nickname);
        checkAge(age);

        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.address = address;
    }

    private void checkName(String name) {
        checkLength(name);
        checkCharacterPattern(name);
    }

    private void checkNickname(String nickname) {
        checkLength(nickname);
        checkCharacterAndNumberPattern(nickname);
    }

    private void checkLength(String request) {
        if (request == null || request.isEmpty() || request.length() > MAXIMUM_LENGTH_LIMIT) {
            throw new InvalidRequestValueException("1자 이상 20자 이하로 입력해주세요.");
        }
    }

    private void checkCharacterPattern(String request) {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", request)) {
            throw new InvalidRequestValueException("한글 또는 영어로 입력해주세요.");
        }
    }

    private void checkCharacterAndNumberPattern(String request) {
        if (!Pattern.matches("^[가-힣a-zA-Z0-9]+$", request)) {
            throw new InvalidRequestValueException("한글 또는 영어 또는 숫자로 입력해주세요.");
        }
    }

    private void checkAge(int age) {
        if (age < MINIMUM_AGE_LIMIT) {
            throw new InvalidRequestValueException("나이는 0세보다 적을 수 없습니다.");
        }
    }

    public void updateName(String name) {
        checkName(name);
        this.name = name;
    }

    public void updateNickname(String nickname) {
        checkNickname(nickname);
        this.nickname = nickname;
    }

    public void updateAge(int age) {
        checkAge(age);
        this.age = age;
    }

    public void updateAddress(String address) {
        this.address = address;
    }
}
