package org.duyhung.repository;

import org.duyhung.entity.Order;
import org.duyhung.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Page<Order> findAllByUser_Id(String user_id, Pageable pageable);

    Page<Order> findAllByStatus(Pageable pageable, String status);
}
