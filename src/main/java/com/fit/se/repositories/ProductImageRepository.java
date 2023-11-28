package com.fit.se.repositories;

import com.fit.se.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    String getImageByProduct(long id);
}
