package com.example.food.service;

import com.example.food.model.Order;
import com.example.food.model.User;
import com.example.food.repository.OrderRepository;
import com.example.food.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void makeOrder(Long userId, String address, String city, String country,
                          String zipCode, Double orderPrice, int itemQuantity, int isPaid,
                          int isCompleted, String dateOrder) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setAddress(address);
        order.setCity(city);
        order.setCountry(country);
        order.setZipCode(zipCode);
        order.setOrderPrice(orderPrice);
        order.setItemQuantity(itemQuantity);
        order.setIsPaid(isPaid);
        order.setIsCompleted(isCompleted);
        order.setDateOrder(dateOrder);
        order.setUser(user);
        orderRepository.save(order);
    }
}
