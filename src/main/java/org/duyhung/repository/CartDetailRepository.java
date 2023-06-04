package org.duyhung.repository;

import org.duyhung.entity.Cart;
import org.duyhung.entity.CartDetail;
import org.duyhung.entity.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
    @Query(value =
            "select sum(c.quantity * c.id.product.price) " +
            "from CartDetail c " +
            "group by c.id.cart " +
            "having c.id.cart = ?1")
    Double getGrandTotal(Cart cart);

    Integer deleteAllByIdCart(Cart id_cart);
}
