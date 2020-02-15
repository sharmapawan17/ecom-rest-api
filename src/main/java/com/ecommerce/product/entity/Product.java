package com.ecommerce.product.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Column(name = "product_code")
    protected String productCode;
    private long id;
    private String name;
    private String created;
    private String price;
    private String description;
    @Column(name = "sub_category_id")
    private long subCategoryId;

    public Product() {
    }

    public Product(String id) {
        this.id = Long.parseLong(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String toString() {
        return getName();
    }

}