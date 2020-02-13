package com.ecommerce.product.repository;

import com.ecommerce.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productImageRepository")
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
}

