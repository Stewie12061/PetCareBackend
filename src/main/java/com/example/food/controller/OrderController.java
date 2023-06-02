package com.example.food.controller;

import com.example.food.model.*;
import com.example.food.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok("Create order successfully.");
    }

    @GetMapping("/{userId}/allOrder")
    public List<Order> allOrder(@PathVariable Long userId) {
        return orderService.findByUserId(userId);
    }

    @PutMapping("/{userId}/{orderId}/cancelOrder")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") int orderId, @PathVariable Long userId, @RequestBody CancelOrderResquest request){
        orderService.cancelOrder(userId,request.getIsCompleted(),orderId);
        return ResponseEntity.ok("Order cancel successfully.");
    }

    @DeleteMapping("/{userId}/{orderId}/deleteOrder")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") int orderId, @PathVariable Long userId){
        orderService.deleteOrder(userId,orderId);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
