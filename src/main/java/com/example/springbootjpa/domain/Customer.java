package com.example.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "customer")
@Entity
public class Customer {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

}
