package com.fit.se.repositories;

import com.fit.se.models.ProductPrice;
import com.fit.se.pks.ProductPricePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK> {

    double getNewestPriceByProduct(long id);
}
