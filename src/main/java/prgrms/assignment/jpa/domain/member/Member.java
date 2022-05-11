package prgrms.assignment.jpa.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prgrms.assignment.jpa.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    @Lob
    private String description;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
