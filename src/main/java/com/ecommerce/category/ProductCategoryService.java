package com.ecommerce.category;

import com.ecommerce.category.entity.ProductCategoryEntity;
import com.ecommerce.category.entity.ProductSubCategoryEntity;
import com.ecommerce.category.model.CategoryRequest;
import com.ecommerce.category.model.SubCategoryRequest;
import com.ecommerce.category.repository.ProductCategoryRepository;
import com.ecommerce.category.repository.ProductSubCategoryRepository;
import com.ecommerce.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryService {

    private static final String CATEGORY_IMAGE_ROOT = "category-images/";
    private static final String SUB_CATEGORY_IMAGE_ROOT = "sub-category-images/";
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;
    @Autowired
    private StorageService storageService;

    public List<ProductCategoryEntity> getProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategoryEntity getProductCategory(long categoryId) {
        return productCategoryRepository.findById(categoryId).get();
    }

    public ProductCategoryEntity getOneProductCategoryById(long categoryId) {
        return productCategoryRepository.findById(categoryId).get();
    }

    public ProductCategoryEntity saveCategory(ProductCategoryEntity productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategoryEntity createProductCategory(CategoryRequest category, MultipartFile file) {
        ProductCategoryEntity entity = new ProductCategoryEntity();
        entity.setCategoryName(category.getCategoryName());
        entity.setActive(true);
        ProductCategoryEntity out = productCategoryRepository.save(entity);

        uploadCategoryImageImage(out.getId(), file);
        return null; // todo better handling
    }

    private List<ProductSubCategoryEntity> prepareSubCategoryList(ProductCategoryEntity entity, List<SubCategoryRequest> subCategories) {
        List<ProductSubCategoryEntity> list = new ArrayList<>();
        subCategories.forEach(subCategory -> {
            ProductSubCategoryEntity subCategoryEntity = new ProductSubCategoryEntity();
            subCategoryEntity.setSubCategoryName(subCategory.getSubCategoryName());
            subCategoryEntity.setActive(true);
            list.add(subCategoryEntity);
        });
        return list;
    }

    public ProductSubCategoryEntity editSubCategory(long subCategoryId, SubCategoryRequest subCategoryRequest, MultipartFile file) {
        ProductSubCategoryEntity entity = productSubCategoryRepository.findById(subCategoryId).get();

        if (entity == null) {
            throw new IllegalArgumentException("Invalid Sub CategoryId");
        }
        if (subCategoryRequest.getCategoryId() != null) {
            ProductCategoryEntity productCategoryEntity = productCategoryRepository.findById(subCategoryRequest.getCategoryId()).get();
            if (productCategoryEntity == null) {
                throw new IllegalArgumentException("Invalid Category name provided in request");
            }
            entity.setProductCategory(productCategoryEntity);
        }
        if (subCategoryRequest.getSubCategoryName() != null) {
            entity.setSubCategoryName(subCategoryRequest.getSubCategoryName());
        }
        ProductSubCategoryEntity out = productSubCategoryRepository.save(entity);
        if (file != null) {
            // todo delete the existing image first.
            uploadSubCategoryImageImage(out.getId(), file);
        }
        return out;
    }

    public ProductSubCategoryEntity createProductSubCategory(SubCategoryRequest subCategoryRequest, MultipartFile file) {
        ProductSubCategoryEntity subCategoryEntity = new ProductSubCategoryEntity();
        subCategoryEntity.setSubCategoryName(subCategoryRequest.getSubCategoryName());
        subCategoryEntity.setActive(subCategoryRequest.getActive());

        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findById(subCategoryRequest.getCategoryId()).get();

        if (productCategoryEntity == null) {
            throw new IllegalArgumentException("Invalid Product Category Name");
        }
        subCategoryEntity.setProductCategory(productCategoryEntity);

        ProductSubCategoryEntity out = productSubCategoryRepository.save(subCategoryEntity);
        uploadSubCategoryImageImage(out.getId(), file);
        return out;
    }

    public String uploadCategoryImageImage(long categoryId, MultipartFile file) {
        String path = CATEGORY_IMAGE_ROOT + categoryId;
        String filename = storageService.store(file, path);
        ProductCategoryEntity entity = productCategoryRepository.findById(categoryId).get();
        entity.setImagePath(filename);
        productCategoryRepository.save(entity);
        return filename;
    }

    public String uploadSubCategoryImageImage(long categoryId, MultipartFile file) {
        String path = SUB_CATEGORY_IMAGE_ROOT + categoryId;
        String filename = storageService.store(file, path);
        ProductSubCategoryEntity entity = productSubCategoryRepository.findById(categoryId).get();
        entity.setImagePath(filename);
        productSubCategoryRepository.save(entity);
        return filename;
    }

    public ResponseEntity<Resource> serveCategoryImage(long categoryId) {

        String imagePath = productCategoryRepository.findById(categoryId).get().getImagePath();

        String path = CATEGORY_IMAGE_ROOT + categoryId + "/";

        Resource file = storageService.loadAsResource(path + imagePath);
        String mimeType = "image/png";
        try {
            mimeType = file.getURL().openConnection().getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }

    public ResponseEntity<Resource> serveSubCategoryImage(long subCategoryId) {
        String imagePath = productSubCategoryRepository.findById(subCategoryId).get().getImagePath();

        String path = SUB_CATEGORY_IMAGE_ROOT + subCategoryId + "/";

        Resource file = storageService.loadAsResource(path + imagePath);
        String mimeType = "image/png";
        try {
            mimeType = file.getURL().openConnection().getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }

    public ProductCategoryEntity editProductCategory(long categoryId, CategoryRequest categoryRequest, MultipartFile file) {
        ProductCategoryEntity productCategory = getProductCategory(categoryId);

        if (productCategory == null) {
            throw new IllegalArgumentException("Invalid Product Category ID");
        }

        if (categoryRequest.getCategoryName() != null) {
            productCategory.setCategoryName(categoryRequest.getCategoryName());
        }
        productCategoryRepository.save(productCategory);
        if (file != null) {
            // todo remove old file first ?
            uploadCategoryImageImage(productCategory.getId(), file);
        }
        return productCategory;
    }

    public void deleteCategory(long categoryId) {
        // todo delete the image from file system
        productCategoryRepository.deleteById(categoryId);
    }

    public void deleteSubCategory(long subCategoryId) {
        // todo delete the image from file system
        productSubCategoryRepository.deleteById(subCategoryId);
    }
}
