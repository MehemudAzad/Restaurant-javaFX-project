package com.example.main_project_final.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private String name;
    //Id,Name,Score,Price,ZipCode,Category1,Category2,Category3
    private double score;
    private int id;
    private String price;
    private String category1;
    private String category2;
    private String category3;
    private String zipcode;
    private List<String> categories;//categories list to store the categories
    private List<Food> menuItems;//store the food items for the restaurants

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
       constructors
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public Restaurant(int id, String name, double score, String price, String zipcode, String category1, String category2, String category3){
        menuItems = new ArrayList<>();
        categories = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.score = score;
        this.price = price;
        this.zipcode = zipcode;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);


    }//Id,Name,Score,Price,ZipCode,Category1,Category2,Category3
    public Restaurant(int id, String name, double score, String price, String zipcode, String category1, String category2){
        menuItems = new ArrayList<>();
        categories = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.score = score;
        this.price = price;
        this.zipcode = zipcode;
        this.category1 = category1;
        this.category2 = category2;
        categories.add(category1);
        categories.add(category2);
    }
    public Restaurant(int id, String name, double score, String price, String zipcode,  String category1){
        menuItems = new ArrayList<>();
        categories = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.score = score;
        this.price = price;
        this.zipcode = zipcode;
        this.category1 = category1;
        categories.add(category1);
    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
           getter and setters
        -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public String getPrice(){
        return price;
    }

    public int getFoodCount(){
        return menuItems.size();
    }

    public void setMenuItems(ArrayList<Food> menuItems) {
        this.menuItems = menuItems;
    }
    public List<Food> getMenuItems() {
        return menuItems;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
      helper functions
   -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    //add food
    public void addMenuItems(Food f){
        menuItems.add(f);//no need to keep track of size since array list
    }

    //has category
    public boolean hasCategory(String categoryName){
        return category1.equalsIgnoreCase(categoryName) || category2.equalsIgnoreCase(categoryName) || category3.equalsIgnoreCase(categoryName);
    }

    public List<Food>searchFoodByName(String foodName){
        List<Food>  result = new ArrayList<>();
        for(Food f: menuItems){
            if(f.getName().toLowerCase().contains(foodName.toLowerCase())){
                result.add(f);
            }//getName().toLowerCase().contains(resName.toLowerCase())
        }
        return result;
    }

    public List<Food>searchFoodByCategory(String categoryName){
        List<Food>  result = new ArrayList<>();
        for(Food f: menuItems){
            if(f.getCategory().toLowerCase().contains(categoryName.toLowerCase())){
                result.add(f);
            }//getName().toLowerCase().contains(resName.toLowerCase())
        }
        return result;
    }
    public List<Food>searchFoodByPriceRange(double lowerBound, double upperBound){
        List<Food>  result = new ArrayList<>();
        for(Food f: menuItems){
            if(f.getPrice()>=lowerBound && f.getPrice()<=upperBound){
                result.add(f);
            }//getName().toLowerCase().contains(resName.toLowerCase())
        }
        return result;
    }
    public List<Food> getCostliestFoodItems(){
        List<Food>  result = new ArrayList<>();
        double costliest = 0;
//      find the costliest price
        for (Food f : menuItems) {
            if (f.getPrice() > costliest) {
                costliest = f.getPrice();//store the index
            }
        }
        //display all the costliest items
        for(Food f: menuItems){
            if( f.getPrice() == costliest){
                result.add(f);
            }
        }
        return result;
    }

}
