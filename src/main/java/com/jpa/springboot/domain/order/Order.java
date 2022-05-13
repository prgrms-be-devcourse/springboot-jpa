package com.jpa.springboot.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;


@Setter @Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private Long id;

    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
}