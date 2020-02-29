package com.ecommerce.category.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_category")
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name")
    private String categoryName;
    private Boolean active;
    @Column(name = "create_date", updatable = false)
    private Date createDate;
    @Column(name = "image_path")
    private String imagePath;
    private String image;

    @OneToMany(
            mappedBy = "productCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ProductSubCategoryEntity> productSubCategories = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (createDate == null) {
            createDate = new Date();
        }
    }

    public List<ProductSubCategoryEntity> getProductSubCategories() {
        return productSubCategories;
    }

    public void setProductSubCategories(List<ProductSubCategoryEntity> productSubCategories) {
        this.productSubCategories = productSubCategories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
}
