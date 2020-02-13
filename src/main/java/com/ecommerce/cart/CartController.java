package com.ecommerce.cart;

import com.ecommerce.cart.CartItem;
import com.ecommerce.cart.CartService;
import com.ecommerce.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    Validator orderValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(orderValidator);
    }

    @PostMapping("/")
    public String create() {
        return cartService.createNewCart();
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
        cartService.addProduct(cartId, cartItem);
        return "OK";
    }

    @GetMapping("/{id}")
    public Set<CartItem> getCartItems(@PathVariable("id") String cartId) {
        return cartService.getItems(cartId);
    }

    @DeleteMapping("{id}/{product_id}")
    public String removeItem(@PathVariable("id") String cartId, @PathVariable("product_id") String productId) {
        cartService.removeProduct(cartId, productId);
        return "OK";
    }

    @PostMapping("{id}/quantity")
    public String setProductQuantity(@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
        String productId = Long.toString(cartItem.getProductId());
        cartService.setProductQuantity(cartId, productId, cartItem.getQuantity());
        return "OK";
    }

    @PostMapping("{id}/order")
    public Order createOrder(@PathVariable("id") String cartId, @RequestBody @Valid Order order) {
        if (order == null) {
            System.out.println("Order not in POST");
            return null;
        }
        return cartService.createOrder(cartId, order);
    }
}
