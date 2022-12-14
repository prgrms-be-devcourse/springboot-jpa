package com.prgrms.jpamission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "id")
    private String uuid;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(name = "nick_name", unique = true, nullable = false, length = 20)
    private String nickName;

    @Column(length = 3, nullable = false)
    private int age;

    @Column(unique = true, nullable = false)
    private String address;

    private String description;

    @OneToMany(mappedBy = "members")
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
    }

}
