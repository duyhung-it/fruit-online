package org.duyhung.service.impl;

import org.duyhung.entity.Order;
import org.duyhung.entity.User;
import org.duyhung.repository.OrderRepository;
import org.duyhung.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        if(order.getId() == null){
            order.setCreatedDate(LocalDate.now());
        }else if(order.getStatus().equals("3")){
            order.setPaidDate(LocalDate.now());
        }
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getAllOrdersByUserId(Pageable pageable, User user) {
        return orderRepository.findAllByUser_Id(user.getId(),pageable);
    }

    @Override
    public Page<Order> getAllOrdersByStatus(Pageable pageable, String status) {
        return orderRepository.findAllByStatus(pageable,status);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
