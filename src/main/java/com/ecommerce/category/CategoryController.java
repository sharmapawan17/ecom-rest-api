package com.ecommerce.category;

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

import javax.validation.Valid;
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
    public List<ProductCategoryEntity> productAllCategories() {
        List<ProductCategoryEntity> list = productCategoryService.getProductCategories();
        return list;
    }

    @GetMapping("/{categoryId}")
    public ProductCategoryEntity getOneProductCategory(@PathVariable("categoryId") long categoryId) {
        return productCategoryService.getOneProductCategoryById(categoryId);
    }

    @PostMapping
    public ProductCategoryEntity createCategory(
            @RequestBody @Valid CategoryRequest category) {
        return productCategoryService.createProductCategory(category);
    }

    @PostMapping("{categoryId}/uploadImage")
    public String handleCategoryImageUpload(@PathVariable("categoryId") long categoryId, @RequestParam("file") MultipartFile file) {
        return productCategoryService.uploadCategoryImageImage(categoryId, file);
    }

    @GetMapping("/image/{categoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveCategoryImage(@PathVariable("categoryId") long categoryId) {
        return productCategoryService.serveCategoryImage(categoryId);
    }

    @GetMapping("/sub/image/{subCategoryId}")
    @ResponseBody
    public ResponseEntity<Resource> serveSubCategoryImage(@PathVariable("subCategoryId") long subCategoryId) {
        return productCategoryService.serveSubCategoryImage(subCategoryId);
    }

    @PostMapping("/{categoryId}")
    public ProductCategoryEntity editCategory(@PathVariable(value = "categoryId") long categoryId,
                                              @RequestBody @Valid CategoryRequest categoryRequest) {

        ProductCategoryEntity productCategory = productCategoryService.getProductCategory(categoryId);

        if (productCategory == null) {
            throw new IllegalArgumentException("Invlid Product Category ID");
        }
        return productCategoryService.createProductCategory(categoryRequest);
    }

    @PostMapping("sub/{subCategoryId}/uploadImage")
    public String handleFileUpload(@PathVariable("subCategoryId") long subCategoryId, @RequestParam("file") MultipartFile file) {
        return productCategoryService.uploadSubCategoryImageImage(subCategoryId, file);
    }

    @PostMapping("/subcategory")
    public ProductSubCategoryEntity createSubCategory(
            @RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return productCategoryService.createProductSubCategory(subCategoryRequest);
    }

    @PostMapping("/sub/{subCategoryId}")
    public ProductSubCategoryEntity editSubCategory(@PathVariable(value = "subCategoryId") long subCategoryId, @RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return productCategoryService.editSubCategory(subCategoryId, subCategoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable(value = "categoryId") long categoryId) {
        productCategoryService.deleteCategory(categoryId);
    }
}