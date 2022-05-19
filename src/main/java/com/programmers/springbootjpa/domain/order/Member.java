package com.programmers.springbootjpa.domain.order;

import com.programmers.springbootjpa.domain.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address", nullable = false)    // length = 255 가 default
    private String address;

    @Column(name = "description", nullable = false)    // length = 255 가 default
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    protected Member() {}

    public Member(String name, String nickName, int age, String address, String description) {
        validateName(name);
        validateNickName(nickName);
        validateAge(age);
        validateAddress(address);
        validateDescription(description);

        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    /* Data Validation */

    private void validateName(String name) {
        Assert.notNull(name, "Name should not be null.");
        Assert.isTrue(name.length() <= 20, "Name length should be less than or equal to 20 characters.");
    }

    private void validateNickName(String nickName) {
        Assert.notNull(nickName, "NickName should not be null.");
        Assert.isTrue(nickName.length() <= 30, "NickName length should be less than or equal to 30 characters.");
    }

    private void validateAge(int age) {
        Assert.notNull(age, "Age should not be null.");
        Assert.isTrue(age >= 0, "Age should be greater than or equal to 0 ");
    }

    private void validateAddress(String address) {
        Assert.notNull(address, "Address should not be null.");
        Assert.isTrue(address.length() <= 255, "Address length should be less than or equal to 255 characters.");
    }

    private void validateDescription(String description) {
        Assert.notNull(description, "Description should not be null.");
        Assert.isTrue(description.length() <= 255, "Description length should be less than or equal to 255 characters.");
    }


    public void addOrder(Order order) {
        order.setMember(this);
    }

    /* getter */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
