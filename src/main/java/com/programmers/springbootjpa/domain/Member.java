package com.programmers.springbootjpa.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "MEMBER_TBL")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "password", nullable = false, length = 25)
    private String password;

    @Embedded
    private Address address;

    protected Member() {}

    public Member(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

}
