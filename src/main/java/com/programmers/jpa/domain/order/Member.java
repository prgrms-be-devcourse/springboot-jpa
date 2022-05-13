package com.programmers.jpa.domain.order;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;    // nick_name

    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "member") // 연관관계 주인은 order.member
    private List<Order> orders;

    public void addOrder(Order order) {
        order.setMember(this);
    }

    public Member() {}

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
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

    public void changeName(String name) {
        this.name = name;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
