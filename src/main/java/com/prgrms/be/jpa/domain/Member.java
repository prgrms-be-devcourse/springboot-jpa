package com.prgrms.be.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 10)
    private String name;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(unique = true)
    private String nickName;

    @NotNull
    private int age;

    @NotNull
    private String address;

    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String address) {
        this(name, nickName, age, address, "");
    }

    public Member(String name, String nickName, int age, String address, String description) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백만으로 구성될 수 없습니다.");
        }
        if (nickName.isBlank()) {
            throw new IllegalArgumentException("닉네임은 공백만으로 구성될 수 없습니다.");
        }

        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
