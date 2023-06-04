package org.duyhung.service;

import org.duyhung.entity.*;

import java.util.List;

public interface CartDetailService {
    List<CartDetail> getAllCartDetails();
    CartDetail getCartDetailById(CartDetailId id);

    CartDetail saveCartDetail(CartDetail cart);

    void deleteCartDetail(CartDetailId id);

    CartDetail getCartDetailsByProductId(Product product,User user);

    Double getGrandTotal(Cart cart);

    boolean deleteAllCartDetailByCartId(Cart cart);
}
