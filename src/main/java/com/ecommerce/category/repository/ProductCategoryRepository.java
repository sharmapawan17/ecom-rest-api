package com.ecommerce.category.repository;

import com.ecommerce.category.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    ProductCategoryEntity findByCategoryName(String name);
}
