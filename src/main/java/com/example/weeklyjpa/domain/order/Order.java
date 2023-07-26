package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.BaseTimeEntity;
import com.example.weeklyjpa.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name = "memo")
    private String memo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public Order(String memo, OrderStatus orderStatus, Member member) {
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.member = member;
    }

    public void changeMember(Member member){
        if(Objects.nonNull(this.member)){
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

//
//    @OneToMany(mappedBy = "order") // orderitems가 가지고 있는 order의 이름
//    private List<OrderItem> orderItems;


}
