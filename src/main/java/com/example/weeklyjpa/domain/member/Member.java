package com.example.weeklyjpa.domain.member;

import com.example.weeklyjpa.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    public void setOrder(Order order){
        order.setMember(this);
    }

}
