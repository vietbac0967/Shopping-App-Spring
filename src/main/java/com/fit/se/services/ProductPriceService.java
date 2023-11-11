package com.fit.se.services;

import com.fit.se.repositories.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public double getProductPriceRepository(long id) {
        return productPriceRepository.getNearestPriceByProduct(id);
    }
}
