package com.ecommerce.cart;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartCache {
    private Map<String, List<CartItem>> cartCache = new HashMap<>();

    public void addItemToList(String cartId, CartItem cartItem) {
        List<CartItem> items = cartCache.get(cartId);
        items.add(cartItem);
    }

    public void removeItemFromList(String cartId, CartItem itemToRemove) {
        List<CartItem> items = cartCache.get(cartId);
        items.removeIf(item -> item.getProductId() == itemToRemove.getProductId());
    }

    public List<CartItem> getList(String cartId) {
        return cartCache.get(cartId);
    }

    public void removeItem(String cartId) {
        cartCache.remove(cartId);
    }
}
