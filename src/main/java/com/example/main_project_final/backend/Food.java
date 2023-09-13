package com.example.main_project_final.backend;

import javafx.scene.control.Button;

import java.io.Serializable;

public class Food implements Serializable {
    private int restaurantId;
    private String category;
    private String name;
    private double price;//RestaurantId,Category,Name,Price
    private int orderCnt;
    //button
//    private Button button;

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
               constructors
            -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public Food(int restaurantId, String category, String name, double price){
        this.restaurantId = restaurantId;
        this.category = category;
        this.name = name;
        this.price = price;
//        this.orderCnt = 0;
//        this.button = new Button("Order");
    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
     Helper Functions
  -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public void increaseOrderCnt(int incrementAmount) {
        this.orderCnt += incrementAmount;
    }

    public int incrementOrderCnt() {
        return ++this.orderCnt;
    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
       setter and getters
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public int getOrderCnt() {
        return orderCnt;
    }

    public void setOrderCnt(int orderCnt) {
        this.orderCnt = orderCnt;
    }
//
//    public Button getButton() {
//        return button;
//    }
//
//    public void setButton(Button button) {
//        this.button = button;
//    }
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
