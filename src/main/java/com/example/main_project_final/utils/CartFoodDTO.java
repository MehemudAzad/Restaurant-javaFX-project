package com.example.main_project_final.utils;

public class CartFoodDTO {
    String name;
    double price;
    int itemCount;

    public CartFoodDTO(){

    };
    public CartFoodDTO(String name, double price, int itemCount){
        this.name = name;
        this.price = price;
        this.itemCount = itemCount;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
