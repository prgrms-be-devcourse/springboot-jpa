package com.kdt.lecture.domain.order;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name ="name", nullable = false, length = 30)
    private String name;
    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "description")
    private String description;

    //fk를 관리할 주체의 필드를 mappedBy에 넣기
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //연관관계 편의 메소드
    public void addOrder(Order order){
        order.setMember(this);
    }

}
