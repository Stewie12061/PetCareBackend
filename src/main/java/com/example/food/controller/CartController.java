package com.example.food.controller;

import com.example.food.model.AddCartRequest;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/product/users")
public class CartController {
    private CartService cartService;

    @PostMapping("/{userId}/carts/add")
    public ResponseEntity<String> addToCars(@PathVariable Long userId, @RequestBody AddCartRequest request) {
        cartService.addToCarts(userId, request.getProductId(),request.getProductQuantity());
        return ResponseEntity.ok("Product added to carts successfully.");
    }

//    @GetMapping("/{userId}/carts")
//    public ResponseEntity<List<Product>> getCartProducts(@PathVariable Long userId) {
//        List<CartItem> cartItems = cartService.getCartItems(userId);
//        List<Product> cartProducts = cartItems.stream()
//                .map(CartItem::getProduct)
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(cartProducts);
//    }

    @GetMapping("/{userId}/carts")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }

    @DeleteMapping("/{userId}/carts/{itemId}")
    public ResponseEntity<String> removeFromCarts(@PathVariable Long userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(itemId, userId);
        return ResponseEntity.ok("Product removed from carts successfully.");
    }

    @DeleteMapping("/{userId}/carts/removeAll")
    public ResponseEntity<String> removeAllFromCarts(@PathVariable Long userId) {
        cartService.removeAllItemFromCart(userId);
        return ResponseEntity.ok("All products removed from carts successfully.");
    }

    @GetMapping("/{userId}/carts/total-price")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long userId) {
        double totalPrice = cartService.getTotalPrice(userId);
        return ResponseEntity.status(HttpStatus.OK).body(totalPrice);
    }

}
