package org.duyhung.repository;

import org.duyhung.entity.Cart;
import org.duyhung.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart> findByUser_Id(String user_id);

    List<Cart> findAllByUser(User user);
}
