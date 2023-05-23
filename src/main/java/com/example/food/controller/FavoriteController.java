package com.example.food.controller;

import com.example.food.model.AddFavoriteRequest;
import com.example.food.model.Product;
import com.example.food.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/product/users")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/favorites/add")
    public ResponseEntity<String> addToFavorites(@PathVariable Long userId, @RequestBody AddFavoriteRequest request) {
        favoriteService.addToFavorites(userId, request.getProductId());
        return ResponseEntity.ok("Product added to favorites successfully.");
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Product>> getFavoriteProducts(@PathVariable Long userId) {
        List<Product> favoriteProducts = favoriteService.getFavoriteProducts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(favoriteProducts);
    }

    @DeleteMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long userId, @PathVariable int productId) {
        favoriteService.removeFromFavorites(productId, userId);
        return ResponseEntity.ok("Product removed from favorites successfully.");
    }
}