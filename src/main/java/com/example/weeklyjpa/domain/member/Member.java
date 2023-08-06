package com.example.weeklyjpa.domain.member;

import com.example.weeklyjpa.domain.BaseTimeEntity;
import com.example.weeklyjpa.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name= "members")
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "nick_name",nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "member") // order의 member를 의미하는 것
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    //양쪽에 대한 변화를 모두 적용해줄 수 있음
    public void addOrder(Order order){
        order.changeMember(this);
    }

}
