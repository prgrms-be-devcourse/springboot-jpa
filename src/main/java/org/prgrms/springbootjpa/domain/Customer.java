package org.prgrms.springbootjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends BaseEntity{
    @Id
    // 일단 실행해보고 nullable, insertable, updatable test
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false, length = 10)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 10)
    private String lastName;

    @Column(name = "full_name", nullable = false, length = 10)
    private String fullName;

    private int age;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer(){}

    public Customer(long id, String firstName, String lastName, String fullName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public void addOrder(Order order){
        order.setCustomer(this);
    }
}
