package com.ecommerce.product;

import com.ecommerce.category.entity.ProductSubCategoryEntity;
import com.ecommerce.category.repository.ProductSubCategoryRepository;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImageEntity;
import com.ecommerce.product.models.ProductRequest;
import com.ecommerce.product.models.ProductResponse;
import com.ecommerce.product.repository.ProductImageRepository;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${app.host}")
    private String HOST;
    @Value("${app.port}")
    private String PORT;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    public Product updateProduct(long id, ProductRequest product) {
        Product updatedProduct = productRepository.findById(id).get();
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setProductCode(product.getProductCode());
        if (product.getSubCategoryName() != null) {
            ProductSubCategoryEntity subCategoryEntity = productSubCategoryRepository.findBySubCategoryName(product.getSubCategoryName());
            if (subCategoryEntity == null) {
                throw new IllegalArgumentException("Invalid Sub Category name");
            } else {
                updatedProduct.setSubCategoryId(subCategoryEntity.getId());
            }
        }
        return productRepository.save(updatedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setDescription(product.getDescription());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setImages(getProductImages(product.getId()));
            productResponseList.add(response);
        });
        return productResponseList;
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).get();
    }

    //todo createProduct method does the same right ? remove this then.
    public Product saveProduct(ProductRequest productRequest) {
        ProductSubCategoryEntity subCategoryEntity = productSubCategoryRepository.findBySubCategoryName(productRequest.getSubCategoryName());
        if (subCategoryEntity == null) {
            throw new IllegalArgumentException("Invalid Sub Category ID");
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setSubCategoryId(subCategoryEntity.getId());
        return productRepository.save(product);
    }

    public ProductImageEntity addProductImage(String id, String filename) {
        ProductImageEntity image = new ProductImageEntity();
        image.setProductId(Long.parseLong(id));
        image.setPath(filename);
        return productImageRepository.save(image);
    }

    public List<ProductImageEntity> getProductImages(long productId) {
        List<ProductImageEntity> images = productImageRepository.findAll();
        images = images.stream().filter(image -> image.getProductId() == productId).collect(Collectors.toList());
        images.forEach(image -> image.setLink("http://" + HOST + ":" + PORT + "/product/image/" + image.getId()));
        return images;
    }

    public ProductImageEntity getImage(long imageId) {
        return productImageRepository.findById(imageId).get();
    }
}
