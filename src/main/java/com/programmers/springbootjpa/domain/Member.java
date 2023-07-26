package com.programmers.springbootjpa.domain;


import com.programmers.springbootjpa.ui.dto.MemberSaveRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "MEMBER_TBL")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, length = 25)
    private String password;

    private Address address;

    protected Member() {}

    public Member(String name, String password, Address address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public static Member of(MemberSaveRequest memberSaveRequest){
        return new Member(memberSaveRequest.name(), memberSaveRequest.password(), memberSaveRequest.address());
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
