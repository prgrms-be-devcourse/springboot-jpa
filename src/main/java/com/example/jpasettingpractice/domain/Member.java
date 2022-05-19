package com.example.jpasettingpractice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(name = "age", nullable = true)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id && age == member.age && name.equals(member.name)
                && nickName.equals(member.nickName) && address.equals(member.address)
                && description.equals(member.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickName, age, address, description);
    }
}
