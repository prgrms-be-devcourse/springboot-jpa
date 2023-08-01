package prgrms.lecture.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 30, unique = true)
    private String nickName;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String description;
    private String address;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String description, String address) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.description = description;
        this.address = address;
    }
}
