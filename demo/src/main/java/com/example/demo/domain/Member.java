package com.example.demo.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
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

    private String age;

    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
