package com.example.main_project_final.utils;

import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDTO implements Serializable {
    String customerName;
    ConcurrentHashMap<Food, Integer> orderList;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    Restaurant restaurant;

    //constructors
    public OrderDTO(){
//        orderList = new ArrayList<>();
        this.orderList = new ConcurrentHashMap<>();
        this.restaurant = null;
    }
    public OrderDTO(Restaurant restaurant){
        this.orderList = new ConcurrentHashMap<>();
        this.restaurant = restaurant;
    }
    public OrderDTO(Restaurant restaurant, String customerName){
        this.customerName = customerName;
        this.orderList = new ConcurrentHashMap<>();
        this.restaurant = restaurant;
    }

    public void addFoodToOrder(Food food, int cnt){
        orderList.put(food, cnt);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ConcurrentHashMap<Food, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(ConcurrentHashMap<Food, Integer> orderList) {
        this.orderList = orderList;
    }
    public void print(){
        for (ConcurrentHashMap.Entry<Food, Integer> entry : orderList.entrySet()) {
            Food key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Key: " + key.getName() + ", Value: " + value);
        }
        System.out.println();
    }
    public double totalPrice(){
        double total = 0;
//        orderList.forEach((key, value) -> {
//            System.out.println("Key: " + key + ", Value: " + value);
//            total += value*key.getPrice();
////        });
//        for (orderList.Entry<String, Integer> entry : orderList.entrySet()) {
//            Food key = e;
//            Integer value = entry.getValue();
//            System.out.println("Key: " + key + ", Value: " + value);
//        }

        for (Integer value : orderList.values()) {
            total += value;
        }

        return total;
    }


}
