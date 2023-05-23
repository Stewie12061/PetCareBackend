package com.example.food.repository;

import com.example.food.model.Favorite;
import com.example.food.model.Product;
import com.example.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Integer> {

    @Query("SELECT f.product FROM Favorite f WHERE f.user.id = :userId")
    List<Product> findFavoriteProductsByUserId(@Param("userId") Long userId);

    Optional<Favorite> findByProductIdAndUser(int productId, User user);

}
