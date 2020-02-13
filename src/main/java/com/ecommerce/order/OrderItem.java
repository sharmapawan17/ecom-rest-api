package com.ecommerce.order;

import com.ecommerce.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    private Integer id;
    private String price;

    private Order order;
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JsonBackReference(value = "product_reference")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
