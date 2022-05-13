package com.kdt.lecture.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 20, unique = true)
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false, length = 40)
    private String address;

    @Column(name = "description")
    private String description;

    //mappedBy를 통하여 연관관계의 주체를 명시함
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(){}

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
