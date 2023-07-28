package com.example.kdt.spring.jpa.domain.order;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    @NotBlank
    private String nickName;

    @Column(nullable = false)
    @Min(value = 1, message = "나이는 최소 1살입니다.")
    private int age;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Nullable
    private String description;

    @OneToMany(mappedBy = "member")
    @NotNull
    private List<Order> orders;

    @Builder
    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
        this.orders = new ArrayList<>();
    }
}
