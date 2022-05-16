package com.programmers.jpa.member;

import com.programmers.jpa.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    public Member(String name, int age, String address, String description) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.description = description;
    }

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Order> orderList = new ArrayList<>();

}
