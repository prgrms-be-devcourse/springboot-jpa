package com.example.springjpa.domain.order;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 50, unique = true)
    private String nickName;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String address;
    private String description;
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    protected Member() {
    }

    public Member(String name, String nickName, int age, String address, String description, List<Order> orders) {
        this.orders = orders;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
