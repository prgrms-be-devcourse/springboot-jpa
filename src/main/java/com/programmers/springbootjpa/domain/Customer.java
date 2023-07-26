package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(name = "address", nullable = false, length = 100)
    private String address;


    private Customer(String name, Integer age, String nickName, String address) {
        this.name = name;
        this.age = age;
        this.nickName = nickName;
        this.address = address;
    }

    public static Customer of(CustomerCreateRequest request) {
        return new Customer(
                request.getName(),
                request.getAge(),
                request.getNickName(),
                request.getAddress()
        );
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAge(Integer age) {
        this.age = age;
    }

    public void updateAddress(String address) {
        this.address = address;
    }
}
