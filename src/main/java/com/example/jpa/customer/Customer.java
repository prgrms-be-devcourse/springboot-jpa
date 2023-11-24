package com.example.jpa.customer;

import com.example.jpa.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String nickname;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    private Customer(String name, String nickname, String password, String username) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    public static Customer of(CustomerSaveRequestDto requestDto) {
        return new Customer(
                requestDto.name(),
                requestDto.nickname(),
                requestDto.password(),
                requestDto.username()
        );
    }

    public Long updateInfo(CustomerUpdateRequestDto requestDto) {
        this.username = requestDto.username();
        this.password = requestDto.password();
        this.nickname = requestDto.nickname();
        return this.id;
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }
}
