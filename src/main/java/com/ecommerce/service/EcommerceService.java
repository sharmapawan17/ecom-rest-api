package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductGroup;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.repository.GroupRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcommerceService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    //    @Autowired
//    private SessionFactory sessionFactory;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        return productRepository.findById(id).get();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public String addProductImage(final String productId, final String filename) {
        ProductImage image = new ProductImage();
        image.setProductId(Long.parseLong(productId));
        image.setPath(filename);
        productImageRepository.save(image);
        return "";
    }

    public List<ProductImage> getProductImages(long id) {
        //todo fetch only given products images from DB
        List<ProductImage> productImages = productImageRepository.findAll();
        productImages.stream().filter(productImage -> productImage.getId() != id);
        return productImages;
    }

    public List<ProductGroup> getGroups() {
        return groupRepository.findAll();
    }

    public ProductGroup getGroup(long id) {
        return groupRepository.findById(id).get();
    }

    public ProductGroup saveGroup(ProductGroup updatedGroup) {
        return groupRepository.save(updatedGroup);
    }

    public void deleteGroup(long id) {
        groupRepository.deleteById(id);
    }
}
