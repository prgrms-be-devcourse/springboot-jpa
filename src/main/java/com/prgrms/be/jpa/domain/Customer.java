package com.prgrms.be.jpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @NotNull
    private Long id;

    @Size(min = 1, max = 5, message = "이름을 1 ~ 5자 이내로 입력해주세요.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String firstName;

    @Size(min = 1, max = 2, message = "성을 1 ~ 2자 이내로 입력해주세요.")
    @NotBlank(message = "성을 입력해주세요.")
    private String lastName;

    public Customer(String firstName, String lastName) {
        validation(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeName(String firstName, String lastName) {
        validation(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validation(String firstName, String lastName) {
        checkArgument(!firstName.isBlank(), "이름이 비어있거나 공백으로만 이루어질 수 없습니다.", firstName);
        checkArgument(1 <= firstName.length() && firstName.length() <= 5, "이름의 길이 범위는 1자 ~ 5자 이내여야 합니다.", firstName);
        checkArgument(!lastName.isBlank(), "성이 비어있거나 공백으로만 이루어질 수 없습니다.", lastName);
        checkArgument(1 <= lastName.length() && lastName.length() <= 2, "성의 길이 범위는 1자 ~ 2자 이내여야 합니다.", lastName);
    }
}
