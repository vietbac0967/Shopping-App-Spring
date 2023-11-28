package com.fit.se.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NamedQueries({
        @NamedQuery(
                name = "Order.getOrderNewest",
                query = "select o from Order o order by o.id desc limit 1"
        ),
        @NamedQuery(
                name = "Order.getTotalPriceOrderByDate",
                query = "select o.orderDate,SUM(od.price) from Order o inner join OrderDetail od on o.id = od.order.id where o.orderDate between :fromDate and :toDate group by o.orderDate"
        ),
        @NamedQuery(
                name = "Order.getOrderByDate",
                query = "select o from Order o where o.orderDate BETWEEN :fromDate AND :toDate"
        )
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", employee=" + employee.getId() +
                ", customer=" + customer.getId() +
                '}';
    }
}
