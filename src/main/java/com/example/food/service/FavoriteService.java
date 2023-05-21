package com.example.food.service;

import com.example.food.model.Favorite;
import com.example.food.model.Product;
import com.example.food.model.User;
import com.example.food.repository.FavoriteRepository;
import com.example.food.repository.ProductRepository;
import com.example.food.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;

    // Constructor injection

    public void addToFavorites(Long userId, int productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepository.save(favorite);
    }

//    public List<Product> getFavoriteProducts(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        List<Favorite> favorites = favoriteRepository.findByUser(user);
//
//        List<Product> favoriteProducts = favorites.stream()
//                .map(Favorite::getProduct)
//                .collect(Collectors.toList());
//
//        return favoriteProducts;
//    }

    public List<Product> getFavoriteProducts(Long userId) {
        return favoriteRepository.findFavoriteProductsByUserId(userId);
    }

    public void removeFromFavorites(int productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Favorite favorite = favoriteRepository.findByProduct_IdAndUser(productId, user)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }
}