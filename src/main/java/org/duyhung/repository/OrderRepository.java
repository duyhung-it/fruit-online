package org.duyhung.repository;

import org.duyhung.entity.Order;
import org.duyhung.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Page<Order> findAllByUser_Id(String user_id, Pageable pageable);

    Page<Order> findAllByStatus(Pageable pageable, String status);

    @Query(value = "select sum(o.totalPrice) from Order o where o.createdDate between ?1 and ?2")
    Long getRevenueInMonth(LocalDate startDate, LocalDate endDate);
    @Query(value = "select sum(o.totalPrice) from Order o where month(o.createdDate) = ?1 and year(o.createdDate) = ?2")
    Long getRevenueInMonth(int month,int year);
}
