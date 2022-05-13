package com.kdt.lecture.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nickName", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.addMember(this);
    }
}
