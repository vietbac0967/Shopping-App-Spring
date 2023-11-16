package com.fit.se.services;

import com.fit.se.enums.ProductStatus;
import com.fit.se.models.Product;
import com.fit.se.repositories.ProductRepository;
import com.fit.se.utils.PageRender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    public Page<Product> findPaginated(Pageable pageable, Optional<String> keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Product> products;
        if (keyword.isPresent()) {
            products = productRepository.findByKeyWord(keyword.get());
        } else {
            products = productRepository.findProducts();
        }
        PageRender<Product> productPageRender = new PageRender<>();
        List<Product> list = productPageRender.getPageOfModel(products.size(), startItem, pageSize, products);
//        if (products.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, products.size());
//            list = products.subList(startItem, toIndex);
//        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());
    }

    public void deleteProduct(long id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setId(id);
            product.setStatus(ProductStatus.TERMINATED);
            productRepository.save(product);
        }
    }

}
