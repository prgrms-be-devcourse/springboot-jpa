package org.prgms.springbootjpa.mission3.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.springbootjpa.mission3.domain.BaseEntity;
import org.prgms.springbootjpa.mission3.domain.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private OrderStatus orderStatus;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Order(OrderStatus orderStatus, String memo, Member member) {
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
    }
}
