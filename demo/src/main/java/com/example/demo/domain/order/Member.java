package com.example.demo.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 30)
    @Size(min = 1, max = 30)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    @Size(min = 1, max = 30)
    private String nickName;

    private int age;

    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
