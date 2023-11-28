package com.fit.se.services;

import com.fit.se.repositories.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public double getProductPriceRepository(long id) {
        return productPriceRepository.getNearestPriceByProduct(id);
    }

    public Map<LocalDateTime, Double> getPriceFollowData(long id) {
        try {
            List<Object[]> resultList = productPriceRepository.getPriceFollowTime(id);

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
                System.out.println("Query returned no results for id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
            e.printStackTrace(); // Log or handle the exception as needed
        }
        return new TreeMap<>();
    }
}
