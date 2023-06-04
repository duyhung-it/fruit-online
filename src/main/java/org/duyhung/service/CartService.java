package org.duyhung.service;

import org.duyhung.entity.Cart;
import org.duyhung.entity.User;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartsByUser(User user);
    Cart getCartById(String id);

    Cart saveCart(Cart cart);

    void deleteCart(Cart cart);
}
