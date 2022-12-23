package com.prgrms.be.jpa.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "nick_name", unique = true)
    private String nickName;

    @NotNull
    @Column(name = "age")
    private int age;

    @NotNull
    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String address) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
    }

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
