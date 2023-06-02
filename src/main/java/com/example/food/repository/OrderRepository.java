package com.example.food.repository;

import com.example.food.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "SELECT * FROM orders a WHERE a.user_id like %:userId% order by a.is_completed asc, a.date_order" ,nativeQuery = true)
    List<Order> findByUserId(Long userId);

    @Query(value = "SELECT * FROM orders a WHERE a.user_id like %:userId% and a.id like %:orderId%" ,nativeQuery = true)
    Optional<Order> findByUserIdAndOrderId(Long userId, int orderId);
}
