package com.ecommerce.category.model;

public class SubCategoryRequest {
    private String categoryName;
    private String subCategoryName;
    private Boolean active;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
