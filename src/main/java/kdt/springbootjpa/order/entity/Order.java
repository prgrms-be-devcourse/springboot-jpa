package kdt.springbootjpa.order.entity;

import kdt.springbootjpa.customer.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="orders")
public class Order {

    @Id
    private String uuid;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob
    private String memo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Builder
    public Order(String uuid, LocalDateTime orderDatetime, OrderStatus orderStatus, String memo, Member member) {
        this.uuid = uuid;
        this.orderDatetime = orderDatetime;
        this.orderStatus = orderStatus;
        this.memo = memo;
        this.member = member;
    }

    public void setMember(Member member){
        this.member = member;
    }
}
