package org.prgms.springbootjpa.mission3.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.springbootjpa.mission3.domain.BaseEntity;
import org.prgms.springbootjpa.mission3.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(length = 12, nullable = false, unique = true)
    private String nickname;

    private int age;

    @Column(length = 50, nullable = false)
    private String address;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickname, int age, String address) {
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.address = address;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
