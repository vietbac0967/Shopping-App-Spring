package com.fit.se.models;

import com.fit.se.pks.ProductPricePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_price")
@IdClass(ProductPricePK.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "ProductPrice.getNearestPriceByProduct",query = "select p.price from ProductPrice p " +
                "where p.product.id =: id order by p.priceDateTime limit 1"
        ),
        @NamedQuery(
                name = "ProductPrice.getPriceFollowTime",
                query = "select pr.priceDateTime,pr.price from ProductPrice pr where pr.product.id =: id"
        )

})
public class ProductPrice {
    @Id
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    @Id
    @Column(name = "price_date_time")
    private LocalDateTime priceDateTime;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "note")
    private String note;

    public ProductPrice(Product product, LocalDateTime priceDateTime) {
        this.product = product;
        this.priceDateTime = priceDateTime;
    }
}
