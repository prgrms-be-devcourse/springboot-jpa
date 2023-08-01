package com.blackdog.springbootjpa.domain.customer.model;

import com.blackdog.springbootjpa.domain.customer.service.CustomerDto;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import com.blackdog.springbootjpa.domain.customer.vo.Name;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Embedded
    private Email email;

    @Builder
    protected Customer(
            Name name,
            Age age,
            Email email
    ) {
        validateCustomer(name, age, email);
        this.name = name;
        this.age = age;
        this.email = email;
    }

    private void validateCustomer(Name name, Age age, Email email) {
        Assert.notNull(name, "name이 존재하지 않습니다.");
        Assert.notNull(age, "age가 존재하지 않습니다.");
        Assert.notNull(email, "email이 존재하지 않습니다.");
    }

    public void changeCustomer(CustomerDto customerDto) {
        changeName(customerDto.name());
        changeAge(customerDto.age());
        changeEmail(customerDto.email());
    }

    private void changeName(Name name) {
        this.name = name;
    }

    private void changeAge(Age age) {
        this.age = age;
    }

    private void changeEmail(Email email) {
        this.email = email;
    }

}
