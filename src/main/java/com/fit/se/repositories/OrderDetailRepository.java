package com.fit.se.repositories;

import com.fit.se.models.OrderDetail;
import com.fit.se.pks.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {

    Optional<Double> getTotalPriceOfOrder(long id);
}
