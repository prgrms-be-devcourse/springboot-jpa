package com.prgrms.springbootjpa.domain.order;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member() {
    }

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public Member(String name, String nickName, int age, String address) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
