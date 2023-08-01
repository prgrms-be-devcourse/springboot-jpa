package prgrms.lecture.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String id;
    private String memo;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(String id, String memo, OrderStatus orderStatus, LocalDateTime orderDatetime, Member member) {
        this.id = id;
        this.memo = memo;
        this.orderStatus = orderStatus;
        this.orderDatetime = orderDatetime;
        this.member = member;
        if(Objects.nonNull(member)) {
            member.getOrders().add(this);
        }
    }
}
