package com.blackdog.springbootjpa.domain.customer.model;

import com.blackdog.springbootjpa.domain.customer.service.CustomerDto;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import com.blackdog.springbootjpa.domain.customer.vo.Name;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customer")
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Embedded
    private Email email;

    @Builder
    public Customer(
            Name name,
            Age age,
            Email email
    ) {
        this.name = name;
        this.age = age;
        this.email = email;
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
