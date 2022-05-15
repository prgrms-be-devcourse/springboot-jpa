package com.example.springbootjpa.domain.main;

import com.example.springbootjpa.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
@Entity
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    private String uuid;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Lob
    private String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
    }

    @Builder
    public Member(@NonNull String uuid,
                  @NonNull String email,
                  String description) {
        super(LocalDateTime.now());
        this.uuid = uuid;
        this.email = email;
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
