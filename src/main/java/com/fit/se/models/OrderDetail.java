package com.fit.se.models;

import com.fit.se.pks.OrderDetailPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderDetailPK.class)
@Builder

@NamedQueries({
        @NamedQuery(
                name = "OrderDetail.getTotalPriceOfOrder",
                query = "select SUM(od.price) from OrderDetail od where od.order.id =: id"
        )
})
public class OrderDetail {
    @Column(name = "quantity", nullable = false)
    private double quantity;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "note", length = 255, nullable = true)
    private String note;
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
}
