package com.fit.se.services;

import com.fit.se.dto.Cart;
import com.fit.se.enums.ProductStatus;
import com.fit.se.models.Product;
import com.fit.se.repositories.ProductPriceRepository;
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
    private final ProductPriceRepository productPriceRepository;

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

    public List<Cart> getCartsProduct(ArrayList<Cart> cartList) {
        List<Cart> carts = new ArrayList<>();
        if (!cartList.isEmpty()) {
            for (Cart cart : cartList) {
                Product product = productRepository.findById(cart.getId()).orElseThrow(
                        () -> new IllegalArgumentException("Not found with id: " + cart.getId())
                );
                Cart row = new Cart();
                row.setId(product.getId());
                row.setName(product.getName());
                System.out.println(product.getName());
                row.setDescription(product.getDescription());
                System.out.println(product.getDescription());
                row.setQuantity(cart.getQuantity());
                double price = productPriceRepository.getNearestPriceByProduct(product.getId());
                System.out.println(price);
                row.setPrice(price * cart.getQuantity());
                carts.add(row);
            }
        }
        return carts;
    }

    public double getTotalCartPrice(ArrayList<Cart> cartList) {
        double sum = 0;
        if (!cartList.isEmpty()) {
            for (Cart item : cartList) {
                double price = productPriceRepository.getNearestPriceByProduct(item.getId());
                sum += price * item.getQuantity();
            }
        }
        return sum;
    }

}
