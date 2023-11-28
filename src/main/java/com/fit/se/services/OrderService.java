package com.fit.se.services;

import com.fit.se.models.Order;
import com.fit.se.repositories.OrderRepository;
import com.fit.se.utils.PageRender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Order> orders = orderRepository.findAll();
        PageRender<Order> employeePageRender = new PageRender<>();
        List<Order> list = employeePageRender.getPageOfModel(orders.size(), startItem, pageSize, orders);
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), orders.size());
    }

    public Map<LocalDateTime, Double> getTotalPriceByDate(LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            List<Object[]> resultList = orderRepository.getTotalPriceOrderByDate(fromDate, toDate);
            if (resultList != null && !resultList.isEmpty()) {
                return resultList.parallelStream()
                        .collect(Collectors.toMap(
                                objects -> ((LocalDateTime) objects[0]),
                                objects -> {
                                    Number value = (Number) objects[1];
                                    return value != null ? value.doubleValue() : 0.0;
                                },
                                (n1, n2) -> n2,
                                TreeMap::new
                        ));
            } else {
                System.out.println("Query returned no results for date is: " + fromDate + "and to date " + toDate);
            }
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
        return new TreeMap<>();
    }
}
