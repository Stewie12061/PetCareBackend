package com.example.food.model;

public class CartDTO {
    String productId;
    String userId;
    String productQuantity;

    public CartDTO(String productId, String userId, String productQuantity) {
        this.productId = productId;
        this.userId = userId;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
