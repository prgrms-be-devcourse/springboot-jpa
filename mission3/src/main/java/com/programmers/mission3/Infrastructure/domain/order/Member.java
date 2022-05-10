package com.programmers.mission3.Infrastructure.domain.order;

import com.programmers.mission3.Infrastructure.domain.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "nick_name", length = 30, nullable = false, unique = true)
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public void addOrder(Order order){
        order.setMember(this);
    }

    @Builder
    public Member(
            Long id,
            String name,
            String nickName,
            int age,
            String address,
            String description)
    {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }


    void updateName(String name){
        this.name = name;
    }

    void updateNickName(String nickName){
        this.nickName = nickName;
    }

    void updateAge(int age){
        this.age = age;
    }

    void updateAddress(String address){
        this.address = address;
    }

    void updateDescription(String description){
        this.description = description;
    }
}
