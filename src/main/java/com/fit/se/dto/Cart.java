package com.fit.se.dto;

import com.fit.se.enums.ProductStatus;
import com.fit.se.models.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends Product {
    private int quantity;
    private double price;

    @Override
    public String toString() {
        return "Cart{" +
                "quantity=" + quantity +
                ", price=" + price +
                "} " + super.toString();
    }
}
