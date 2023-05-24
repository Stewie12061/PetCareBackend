package com.example.food.repository;

import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByUserAndProduct(User user, Product product);

    List<CartItem> findCartItemsByUserId(Long userId);


    List<CartItem> findByUserId(Long userId);

    @Query(value = "SELECT * FROM cart_items crt WHERE crt.user_id like %:userId% and crt.id like %:itemId% ",nativeQuery = true)
    Optional<CartItem> findByItemIdAndUser(int itemId, Long userId);
}
