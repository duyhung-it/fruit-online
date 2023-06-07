package org.duyhung.service;

import org.duyhung.entity.Order;
import org.duyhung.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);

    List<Order> getAllOrders();

    Page<Order> getAllOrders(Pageable pageable);
    Page<Order> getAllOrdersByUserId(Pageable pageable, User user);
    Page<Order> getAllOrdersByStatus(Pageable pageable, String status);
    Order getOrderById(String id);
    void updateOrderStatus(String[] orderId);
    void deleteOrder(String id);
    void cancelOrder(String id);
}