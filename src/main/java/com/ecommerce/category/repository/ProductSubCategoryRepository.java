package com.ecommerce.category.repository;

import com.ecommerce.category.entity.ProductSubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategoryEntity, Long> {
    ProductSubCategoryEntity findBySubCategoryName(String name);
}
