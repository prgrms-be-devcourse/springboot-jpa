package com.programmers.jpabasic.domain.customer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    public static Name of(String firstName, String lastName) {
        return Name.builder()
            .firstName(firstName)
            .lastName(lastName)
            .build();
    }
}
