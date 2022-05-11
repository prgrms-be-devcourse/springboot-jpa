package com.example.springjpa.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="customers")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    public Customer(){
    }

    public Customer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
