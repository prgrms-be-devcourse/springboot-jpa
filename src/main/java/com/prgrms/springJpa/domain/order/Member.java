package com.prgrms.springJpa.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "member")
public class Member {
    private static final int NAME_MAX_LENGTH = 30;
    private static final int NICK_NAME_MAX_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "nick_name", nullable = false, length = NICK_NAME_MAX_LENGTH, unique = true)
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    protected Member() {
    }

    public Member(String name, String nickName, int age, String address) {
        this(name, nickName, age, address, null);
    }

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
        validateName(name);
        validateNickName(nickName);
    }

    private void validateName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 null이 될 수 없습니다.");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("이름은 30자 초과할 수 없습니다.");
        }
    }

    private void validateNickName(String nickName) {
        if (Objects.isNull(nickName)) {
            throw new IllegalArgumentException("닉네임은 null이 될 수 없습니다.");
        }

        if (nickName.length() > NICK_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임은 30자 초과할 수 없습니다.");
        }
    }

    public void addOrder(Order order) {
        order.changeMember(this);
    }
}
