package com.example.food.service;

import com.example.food.model.CartItem;
import com.example.food.model.Favorite;
import com.example.food.model.Product;
import com.example.food.model.User;
import com.example.food.repository.CartRepository;
import com.example.food.repository.ProductRepository;
import com.example.food.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public void addToCarts(Long userId, int productId, int productQuantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        CartItem cartItem = cartRepository.findByUserAndProduct(user, product);

        if (cartItem != null) {
            int oldQuantity = cartItem.getQuantity();
            cartItem.setQuantity(oldQuantity + productQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(productQuantity);
        }

        cartRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long userId) {
        return cartRepository.findCartItemsByUserId(userId);
    }

    public void removeItemFromCart(int itemId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartItem cartItem = cartRepository.findByItemIdAndUser(itemId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        cartRepository.delete(cartItem);
    }

    public double getTotalPrice(Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        return totalPrice;
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartRepository.findCartItemsByUserId(userId);
    }

    public void removeAllItemFromCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<CartItem> cartItems = cartRepository.findByUser(user);

        cartRepository.deleteAll(cartItems);
    }
}
