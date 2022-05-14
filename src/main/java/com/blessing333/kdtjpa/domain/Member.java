package com.blessing333.kdtjpa.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    @Singular
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        getOrders().add(order);
        order.changeOrderer(this);
    }

    @Builder
    private Member(String name, String nickName, String address, Order order) {
        setName(name);
        setNickName(nickName);
        setAddress(address);
        if (order != null)
            addOrder(order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member other = (Member) o;
        return id != null && Objects.equals(this.id, other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
