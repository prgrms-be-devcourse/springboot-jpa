package kdt.springbootjpa.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    public void addOrder(Order order) {
        order.setMember(this);
    }

    @Builder
    public Member(Long id, String name, String address, String description, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.orders = orders;
    }
}
