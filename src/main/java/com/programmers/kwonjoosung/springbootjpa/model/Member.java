package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    @Size(min = 2, max = 30, message = "이름은 2자 이상 20자 이하입니다.")
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하입니다.")
    private String nickName;

    @Column(nullable = false)
    @PositiveOrZero(message = "나이는 0 이상이어야 합니다.")
    private int age;

    @Column(name = "addres", nullable = false)
    @Pattern(regexp = "^[가-힣]{2,5} [가-힣]{2,5} [가-힣]{2,5}$", message = "주소는 '시도 시군구 읍면동' 형식으로 입력해주세요.")
    private String address;

    @Column(name = "description")
    @Size(max = 100, message = "자기소개는 100자 이하로 입력해주세요.")
    private String description;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Builder
    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

}