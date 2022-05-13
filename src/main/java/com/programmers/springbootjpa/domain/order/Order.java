package com.programmers.springbootjpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")     // MySQL 등 대부분 DB에서 keyword로 동작하고 있어서 order에 s붙여줌
public class Order {
    @Id
    @Column(name = "id")
    private String uuid;
    @Column(name = "memo")
    private String memo;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDateTime;

    // member_fk
    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    // order는 N, member는 1 -> 다대일 관계
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    // 연관관계 편의 메소드
    public void setMember(Member member) {
        // 이미 있다면 지워주기
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

}
