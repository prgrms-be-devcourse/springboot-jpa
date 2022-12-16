package com.prgms.springbootjpa.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FIRST_NAME", length = 50)
    @NotNull
    @Size(min = 1, max = 50, message
            = "이름 '${validatedValue}' 의 길이는 {min}이상 {max}이하여야 합니다.")
    private String firstName;
    @Column(name = "LAST_NAME", length = 50)
    @NotNull
    @Size(min = 1, max = 50, message
            = "성 '${validatedValue}' 의 길이는 {min}이상 {max}이하여야 합니다.")
    private String lastName;
    @Transient
    private String koreanName;


    public Customer(@NotNull String firstName, @NotNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.koreanName = lastName + firstName;
    }

    public void modifyFirstName(@NotNull String firstName) {
        this.firstName = firstName;
        this.koreanName = this.lastName + this.firstName;
    }

    public void modifyLastName(@NotNull String lastName) {
        this.lastName = lastName;
        this.koreanName = this.lastName + this.firstName;
    }
}
