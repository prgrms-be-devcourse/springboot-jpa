package kdt.springbootjpa.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String address;
    private String description;

    @OneToMany(mappedBy = "member") //연관관계 관리 주체 : Order의 member 필드
    private List<Order> orders;

    public void addOrder(Order order) { //연관관계 편의 메소드, member와 order를 연결
        orders.add(order);
        order.setMember(this);
    }

    @Builder
    public Member(Long id, String name, String address, String description, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.orders = new ArrayList<>();
    }
}
