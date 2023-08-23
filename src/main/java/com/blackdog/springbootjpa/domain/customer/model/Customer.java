package com.blackdog.springbootjpa.domain.customer.model;

import com.blackdog.springbootjpa.domain.customer.service.CustomerDto;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.CustomerName;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import com.blackdog.springbootjpa.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CustomerName customerName;

    @Embedded
    private Age age;

    @Embedded
    private Email email;

    @Builder
    protected Customer(
            CustomerName customerName,
            Age age,
            Email email
    ) {
        changeName(customerName);
        changeAge(age);
        changeEmail(email);
    }

    public String getCustomerNameValue() {
        return customerName.getValue();
    }

    public int getAgeValue() {
        return age.getValue();
    }

    public String getEmailAddress() {
        return email.getEmailAddress();
    }

    public void changeCustomer(CustomerDto customerDto) {
        changeName(customerDto.customerName());
        changeAge(customerDto.age());
        changeEmail(customerDto.email());
    }

    private void changeName(CustomerName customerName) {
        Assert.notNull(customerName, "name이 존재하지 않습니다.");
        this.customerName = customerName;
    }

    private void changeAge(Age age) {
        Assert.notNull(age, "age가 존재하지 않습니다.");
        this.age = age;
    }

    private void changeEmail(Email email) {
        Assert.notNull(email, "email이 존재하지 않습니다.");
        this.email = email;
    }

}
