package com.programmers.jpabasic.domain.customer.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.programmers.jpabasic.global.common.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Embedded
    private Name name;

    public static Customer create(String firstName, String lastName) {
        return Customer.builder()
            .name(Name.of(firstName, lastName))
            .build();
    }

    public void updateName(String firstName, String lastName) {
        this.name = Name.of(firstName, lastName);
    }
}
