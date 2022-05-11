package prgrms.assignment.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;


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

}
