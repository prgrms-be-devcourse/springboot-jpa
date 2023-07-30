package com.blackdog.springbootjpa.domain.customer.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Column(name = "email", length = 50, nullable = false)
    private String emailAddress;
}
