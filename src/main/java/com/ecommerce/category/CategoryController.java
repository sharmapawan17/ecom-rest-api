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
    private CategoryService categoryServiceImpl;
    @Autowired
    private StorageService storageService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(groupValidator);
    }

    @GetMapping
    public List<ProductCategoryEntity> allCategories() {
        List<ProductCategoryEntity> list = categoryServiceImpl.getAllCategories();
        return list;
    }

//    @GetMapping("/subcategories") // todo this API is probably not needed?
    public List<ProductSubCategoryEntity> allSubCategories() {
        List<ProductSubCategoryEntity> list = categoryServiceImpl.getAllSubCategories();
        return list;
    }

    @GetMapping("/{categoryId}")
    public ProductCategoryEntity getOneProductCategory(@PathVariable("categoryId") long categoryId) {
        return categoryServiceImpl.getCategoryById(categoryId);
    }

    @GetMapping("subcategory/{subCategoryId}")
    public ProductSubCategoryEntity getOneProductSubCategory(@PathVariable("subCategoryId") long subCategoryId) {
        return categoryServiceImpl.getSubCategoryById(subCategoryId);
    }

    @Track
    @PostMapping
    public ProductCategoryEntity createCategory(
            CategoryRequest category, @RequestParam("file") MultipartFile file) {
        return categoryServiceImpl.createCategory(category, file);
    }

    @PostMapping("/subcategory")
    public ProductSubCategoryEntity createSubCategory(
            SubCategoryRequest subCategoryRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        return categoryServiceImpl.createSubCategory(subCategoryRequest, file);
    }

    @PostMapping("/{categoryId}")
    public ProductCategoryEntity updateCategory(@PathVariable(value = "categoryId") long categoryId,
                                                CategoryRequest categoryRequest,
                                                @RequestParam(value = "file", required = false) MultipartFile file) {

        return categoryServiceImpl.updateCategory(categoryId, categoryRequest, file);
    }

    @PostMapping("/subcategory/{subCategoryId}")
    public ProductSubCategoryEntity editSubCategory(@PathVariable(value = "subCategoryId") long subCategoryId,
                                                    SubCategoryRequest subCategoryRequest,
                                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        return categoryServiceImpl.updateSubCategory(subCategoryId, subCategoryRequest, file);
    }

    @GetMapping("/image/{categoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveCategoryImage(@PathVariable("categoryId") long categoryId) {
        return categoryServiceImpl.getCategoryImage(categoryId);
    }

    @GetMapping("/subcategory/image/{subCategoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveSubCategoryImage(@PathVariable("subCategoryId") long subCategoryId) {
        return categoryServiceImpl.getSubCategoryImage(subCategoryId);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable(value = "categoryId") long categoryId) {
        categoryServiceImpl.deleteCategory(categoryId);
    }

    @DeleteMapping("/subcategory/{subCategoryId}")
    public void deleteSubCategory(@PathVariable(value = "subCategoryId") long subCategoryId) {
        categoryServiceImpl.deleteSubCategory(subCategoryId);
    }
}