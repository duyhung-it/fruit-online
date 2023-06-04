package org.duyhung.repository;

import org.duyhung.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void saveCartTest(){
        Product product = productRepository.findById("2A1ACAD8-F1D6-4A1E-9FC9-1DFD42F56FF0").orElse(null);
        User user = userRepository.findById("9CCE13F3-CAB0-4B05-A754-A9CA32548923").orElse(null);
        Cart cart = cartRepository.findById("1965ACE1-48EB-44EF-AA93-6BA4A914359B").orElse(null);
        CartDetailId cartDetailId = new CartDetailId();
        cartDetailId.setCart(cart);
        cartDetailId.setProduct(product);
        CartDetail cartDetail = new CartDetail();
        cartDetail.setId(cartDetailId);
//        cartDetail.setQuantity(2);
        assert cart != null;
        cart.getCartDetails().add(cartDetail);
//        cartRepository.save(cart);
    }
}