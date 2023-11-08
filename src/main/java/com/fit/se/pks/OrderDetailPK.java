package com.fit.se.pks;

import com.fit.se.models.Order;
import com.fit.se.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class OrderDetailPK implements Serializable {
    private Order order;
    private Product product;
}
