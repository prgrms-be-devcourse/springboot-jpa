package com.prgms.springbootjpa.domain.order;

import com.prgms.springbootjpa.domain.order.vo.Name;
import com.prgms.springbootjpa.domain.order.vo.NickName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private NickName nickName;

    private int age;

    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    protected Member() {
    }

    public Member(Name name, NickName nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    /* 연관관계 편의 메서드 */
    public void addOrder(Order order) {
        order.assignMember(this);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public NickName getNickName() {
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
