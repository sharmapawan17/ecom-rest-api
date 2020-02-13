package com.ecommerce.category.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_sub_category")
public class ProductSubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sub_category_name")
    private String subCategoryName;
    private Boolean active;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private ProductCategoryEntity productCategory;

    public ProductCategoryEntity getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryEntity productCategory) {
        this.productCategory = productCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSubCategoryEntity that = (ProductSubCategoryEntity) o;
        return subCategoryName.equals(that.subCategoryName) &&
                productCategory.getCategoryName().equals(that.productCategory.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(subCategoryName, productCategory.getCategoryName());
    }
}
