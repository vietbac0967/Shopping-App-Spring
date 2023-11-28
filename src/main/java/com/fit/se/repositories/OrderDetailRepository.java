package com.fit.se.repositories;

import com.fit.se.models.OrderDetail;
import com.fit.se.pks.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {

    double getTotalPriceOfOrder(long id);
}
