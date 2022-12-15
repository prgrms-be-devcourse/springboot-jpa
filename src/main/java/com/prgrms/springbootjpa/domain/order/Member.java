package com.prgrms.springbootjpa.domain.order;

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

    // Order Entity의 member 컬럼이 FK 관리의 주체
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
