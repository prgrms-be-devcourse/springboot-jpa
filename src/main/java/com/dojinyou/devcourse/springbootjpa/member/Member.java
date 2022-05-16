package com.dojinyou.devcourse.springbootjpa.member;

import com.dojinyou.devcourse.springbootjpa.common.entity.BaseEntity;
import com.dojinyou.devcourse.springbootjpa.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    private int birthYear;

    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders;

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Member member = (Member) other;
        return birthYear == member.birthYear
                && Objects.equals(id, member.id)
                && Objects.equals(name, member.name)
                && Objects.equals(nickName, member.nickName)
                && Objects.equals(address, member.address)
                && Objects.equals(description, member.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickName, birthYear, address, description);
    }

    private Member(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.nickName = builder.nickName;
        this.birthYear = builder.birthYear;
        this.address = builder.address;
        this.description = builder.description;
        this.orders = builder.orders;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String nickName;
        private int birthYear;
        private String address;
        private String description;
        private List<Order> orders;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder birthYear(int birthYear) {
            this.birthYear = birthYear;
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

        public Member build() {
            return new Member(this);
        }
    }
}
