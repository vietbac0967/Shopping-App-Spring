package com.fit.se.repositories;

import com.fit.se.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

      Optional<Order> getOrderNewest();

      List<Object[]> getTotalPriceOrderByDate(LocalDateTime fromDate,LocalDateTime toDate);

      List<Order> getOrderByDate(LocalDateTime fromDate,LocalDateTime toDate);
}
