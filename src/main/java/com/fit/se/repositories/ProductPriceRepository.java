package com.fit.se.repositories;

import com.fit.se.models.ProductPrice;
import com.fit.se.pks.ProductPricePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK> {

   double getNearestPriceByProduct(long id);

   List<Object[]> getPriceFollowTime(long id);
}
