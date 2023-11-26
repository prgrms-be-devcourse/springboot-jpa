package org.devcourse.springbootjpa.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.devcourse.springbootjpa.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Setter
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 30) // name varchar(30) not null
    private String name;

    @Column(nullable = false, length = 30, unique = true) // nick_name varchar(30) not null unique
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false) // address varchar(255) not null
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) { // 연관관계 편의 메서드
        order.setMember(this);
    }
}
