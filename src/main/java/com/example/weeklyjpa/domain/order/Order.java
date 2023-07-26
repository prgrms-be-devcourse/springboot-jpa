package com.example.weeklyjpa.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
public class Order {
    @Id
    private Long id;

}
