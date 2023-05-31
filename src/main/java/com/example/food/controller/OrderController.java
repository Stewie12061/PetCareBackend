package com.example.food.controller;

import com.example.food.model.AppointmentRequest;
import com.example.food.model.OrderRequest;
import com.example.food.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/order/users")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/{userId}/makeOrder")
    public ResponseEntity<String> makeOrder(@PathVariable Long userId, @RequestBody OrderRequest request) {
        orderService.makeOrder(userId, request.getAddress(),request.getCity(),
                request.getCountry(),request.getZipCode(), request.getOrderPrice(),
                request.getItemQuantity(),request.getIsPaid(),request.getIsCompleted(),request.getDateOrder());
        return ResponseEntity.ok("Create appointment successfully.");
    }
}
