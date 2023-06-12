package org.duyhung.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Test
    public void testRevenue(){
        System.out.println(orderRepository.getRevenueInMonth(
                LocalDate.of(2023,5,1),
                LocalDate.now()
        ));
    }

}