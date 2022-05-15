package com.kdt.JpaWeekly.domain.member;

import com.kdt.JpaWeekly.common.domain.BaseEntity;
import com.kdt.JpaWeekly.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    @Column
    private String description;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    protected Member() {
        super();
    }

    private Member(Builder builder) {
        super(builder.createdBy, builder.createdAt);
        this.name = builder.name;
        this.nickName = builder.nickName;
        this.age = builder.age;
        this.address = builder.address;
        this.description = builder.description;
        this.orders = builder.orders;
    }

    public static class Builder {
        private String name;
        private String nickName;
        private int age;
        private String address;
        private String description;
        private List<Order> orders;
        private String createdBy;
        private LocalDateTime createdAt;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
}