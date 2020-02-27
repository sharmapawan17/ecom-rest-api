package com.ecommerce.category;

import com.ecommerce.aspect.Track;
import com.ecommerce.category.entity.ProductCategoryEntity;
import com.ecommerce.category.entity.ProductSubCategoryEntity;
import com.ecommerce.category.model.CategoryRequest;
import com.ecommerce.category.model.SubCategoryRequest;
import com.ecommerce.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    Validator groupValidator;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private StorageService storageService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(groupValidator);
    }

    @GetMapping
    public List<ProductCategoryEntity> allCategories() {
        List<ProductCategoryEntity> list = productCategoryService.getProductCategories();
        return list;
    }

    @GetMapping("/{categoryId}")
    public ProductCategoryEntity getOneProductCategory(@PathVariable("categoryId") long categoryId) {
        return productCategoryService.getOneProductCategoryById(categoryId);
    }

    @Track
    @PostMapping
    public ProductCategoryEntity createCategory(
            CategoryRequest category, @RequestParam("file") MultipartFile file) {
        return productCategoryService.createProductCategory(category, file);
    }

    @PostMapping("/{categoryId}")
    public ProductCategoryEntity editCategory(@PathVariable(value = "categoryId") long categoryId,
                                              CategoryRequest categoryRequest,
                                              @RequestParam(value = "file", required = false) MultipartFile file) {

        return productCategoryService.editProductCategory(categoryId, categoryRequest, file);
    }

    @GetMapping("/image/{categoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveCategoryImage(@PathVariable("categoryId") long categoryId) {
        return productCategoryService.serveCategoryImage(categoryId);
    }

    @GetMapping("/subcategory/image/{subCategoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveSubCategoryImage(@PathVariable("subCategoryId") long subCategoryId) {
        return productCategoryService.serveSubCategoryImage(subCategoryId);
    }

    @PostMapping("/subcategory")
    public ProductSubCategoryEntity createSubCategory(
            SubCategoryRequest subCategoryRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        return productCategoryService.createProductSubCategory(subCategoryRequest, file);
    }

    @PostMapping("/subcategory/{subCategoryId}")
    public ProductSubCategoryEntity editSubCategory(@PathVariable(value = "subCategoryId") long subCategoryId,
                                                    SubCategoryRequest subCategoryRequest,
                                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        return productCategoryService.editSubCategory(subCategoryId, subCategoryRequest, file);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable(value = "categoryId") long categoryId) {
        productCategoryService.deleteCategory(categoryId);
    }

    @DeleteMapping("/subcategory/{subcategoryId}")
    public void deleteSubCategory(@PathVariable(value = "subCategoryId") long subCategoryId) {
        productCategoryService.deleteSubCategory(subCategoryId);
    }
}