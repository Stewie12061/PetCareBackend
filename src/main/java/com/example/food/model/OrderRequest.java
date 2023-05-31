package com.example.food.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private Double orderPrice;
    private int itemQuantity;
    private int isPaid;
    private int isCompleted;
    private String dateOrder;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public String getDateOrder() {
        return dateOrder;
    }
}
