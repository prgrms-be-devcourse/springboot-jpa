package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prgrms.assignment.jpa.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;
import static prgrms.assignment.jpa.domain.order.OrderStatus.ACCEPTED;


@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String memo;

    @Enumerated(value = STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(String memo) {
        this.memo = memo;
        this.orderStatus = ACCEPTED;
        this.orderDatetime = now();
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
}
