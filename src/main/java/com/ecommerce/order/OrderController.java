package com.ecommerce.order;

import com.ecommerce.aspect.Track;
import com.ecommerce.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    Validator orderValidator;
    @Autowired
    private EcommerceService ecommerceService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(orderValidator);
    }

    @Track
    @GetMapping
    public List<Order> index() {
        return ecommerceService.getOrders();
    }

    @Track
    @GetMapping("/{id}")
    public Order get(@PathVariable("id") long id) {
        return ecommerceService.getOrder(id);
    }

    @Track
    @PostMapping
    public Order create(@RequestBody @Valid Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return ecommerceService.saveOrder(order);
    }
}
