package com.ecommerce.product;

import com.ecommerce.category.repository.ProductSubCategoryRepository;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImageEntity;
import com.ecommerce.product.models.ProductRequest;
import com.ecommerce.product.repository.ProductImageRepository;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        if (product.getSubCategoryId() != 0){
         if (productSubCategoryRepository.findById(product.getSubCategoryId()).get() == null) {
                 throw new IllegalArgumentException("Invalid Sub Category ID");
         }else{
             updatedProduct.setSubCategoryId(product.getSubCategoryId());
         }
        }
        return productRepository.save(updatedProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).get();
    }

    public Product saveProduct(ProductRequest productRequest) {
        if (productSubCategoryRepository.findById(productRequest.getSubCategoryId()).get() == null){
            throw new IllegalArgumentException("Invalid Sub Category ID");
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setSubCategoryId(productRequest.getSubCategoryId());
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
