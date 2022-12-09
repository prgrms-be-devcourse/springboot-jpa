package com.devcourse.mission.domain.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "customers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column( nullable = false, length = 30)
    private String address;

    @Column(nullable = false)
    private int age;
}
