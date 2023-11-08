package com.fit.se;

import com.fit.se.models.Product;
import com.fit.se.repositories.ProductPriceRepository;
import com.fit.se.repositories.ProductRepository;
import com.fit.se.services.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void deleteProductTest(){

    }
}
