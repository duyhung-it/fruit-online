package org.duyhung.repository;

import org.duyhung.entity.OrderDetail;
import org.duyhung.entity.OrderDetailId;
import org.duyhung.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
