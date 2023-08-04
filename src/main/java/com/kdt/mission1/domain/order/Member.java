package com.kdt.mission1.domain.order;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "description", nullable = false)
    private String description;
}
