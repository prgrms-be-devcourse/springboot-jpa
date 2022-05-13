package com.example.springbootjpa.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    private Long id;

    private String name;

    private int age;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;

    public void addOrder(Order order) {
        this.orderList.add(order);
    }


}
