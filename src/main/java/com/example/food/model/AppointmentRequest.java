package com.example.food.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private int groomingPackageId;
    private String date;
    private String day;
    private String time;
    private Double price;
    private int status;

    public Double getPrice() {
        return price;
    }

    public int getGroomingPackageId() {
        return groomingPackageId;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
