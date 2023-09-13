package com.example.main_project_final.utils;

import java.io.Serializable;

public class LogoutDTO implements Serializable {
    private String restaurantName;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LogoutDTO(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String companyName) {
        this.restaurantName = companyName;
    }
}