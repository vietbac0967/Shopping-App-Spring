package com.fit.se.services;

import com.fit.se.repositories.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public String getPathOfProduct(long id){
        return productImageRepository.getImageByProduct(id);
    }
}
