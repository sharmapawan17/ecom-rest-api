package com.ecommerce.service;

import com.ecommerce.order.Order;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImageEntity;
import com.ecommerce.product.repository.ProductImageRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Value("${app.host}")
    private String HOST;
    @Value("${app.port}")
    private String PORT;

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

    public ProductImageEntity getImage(long id) {
        //todo fetch only given products images from DB
        return productImageRepository.findById(id).get();
    }

    public Page<Product> getAllProducts() {
        return productRepository.findAll(PageRequest.of(0, 2));
    }
}
