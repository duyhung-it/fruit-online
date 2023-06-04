package org.duyhung.service.impl;

import jakarta.transaction.Transactional;
import org.duyhung.entity.*;
import org.duyhung.repository.CartDetailRepository;
import org.duyhung.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Override
    public List<CartDetail> getAllCartDetails() {
        return cartDetailRepository.findAll();
    }

    @Override
    public CartDetail getCartDetailById(CartDetailId id) {
        return cartDetailRepository.findById(id).orElse(null);
    }

    @Override
    public CartDetail saveCartDetail(CartDetail cart) {
        return cartDetailRepository.save(cart);
    }

    @Override
    public void deleteCartDetail(CartDetailId id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public CartDetail getCartDetailsByProductId(Product product, User user) {
        return null;
    }

    @Override
    public Double getGrandTotal(Cart cart) {
        return cartDetailRepository.getGrandTotal(cart);
    }

    @Override
    @Transactional
    public boolean deleteAllCartDetailByCartId(Cart cart) {
        return cartDetailRepository.deleteAllByIdCart(cart) > 0;
    }
}
