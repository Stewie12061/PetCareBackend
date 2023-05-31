package com.example.food.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "groomingPackage_id")
    private GroomingPackage groomingPackage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String date;
    private String day;
    private String time;
    private Double price;
    // 1 is active, 2 is completed, 3 is canceled
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public GroomingPackage getGroomingPackage() {
        return groomingPackage;
    }

    public void setGroomingPackage(GroomingPackage groomingPackage) {
        this.groomingPackage = groomingPackage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
