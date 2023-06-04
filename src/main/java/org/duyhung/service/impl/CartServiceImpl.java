package org.duyhung.service.impl;

import jakarta.transaction.Transactional;
import org.duyhung.entity.Cart;
import org.duyhung.entity.User;
import org.duyhung.repository.CartDetailRepository;
import org.duyhung.repository.CartRepository;
import org.duyhung.service.CartDetailService;
import org.duyhung.service.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

        private final CartRepository cartRepository;
        private final CartDetailService cartDetailService;

        public CartServiceImpl(CartRepository cartRepository, CartDetailService cartDetailService) {
            this.cartRepository = cartRepository;
            this.cartDetailService = cartDetailService;
        }

        @Override
        public List<Cart> getAllCarts() {
            return cartRepository.findAll();
        }

    @Override
    public Cart getCartsByUser(User user) {
        return cartRepository.findByUser_Id(user.getId()).orElse(null);
    }

    @Override
        public Cart getCartById(String id) {
            return cartRepository.findById(id).orElse(null);
        }

        @Override
        public Cart saveCart(Cart cart) {
            if(cart.getId() != null && cart.getId().isEmpty()) cart.setId(null);
            cart.setCreatedDate(LocalDateTime.now());
            cart.setUpdatedDate(LocalDateTime.now());
            return cartRepository.save(cart);
        }

        @Override
        public void deleteCart(Cart cart) {
            if(cartDetailService.deleteAllCartDetailByCartId(cart)){
                cartRepository.delete(cart);
            }
        }
    }