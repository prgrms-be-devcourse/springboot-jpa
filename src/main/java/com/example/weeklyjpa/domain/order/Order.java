package com.example.weeklyjpa.domain.order;

import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.domain.item.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            this.getMember().getOrderList().remove(this);
        }
        this.member = member;
        member.setOrder(this);
    }
}
