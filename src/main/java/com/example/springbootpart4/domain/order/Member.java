package com.example.springbootpart4.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;
    @Column(name = "age")
    private int age;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
