package com.example.main_project_final.utils;

import com.example.main_project_final.backend.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class FileOperations {

    private static final String INPUT_FILE_NAME = "restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "menu.txt";
    private static final String OUTPUT_FILE_NAME = "menu.txt";
//    private static final String PASSWORD_FILE_NAME = "passwords.txt";

    public static ConcurrentHashMap<String, String> setPasswords(){
        ConcurrentHashMap<String,String> passwordList = new ConcurrentHashMap<>();
        passwordList.put("kfc", "1");
        passwordList.put("ihop", "2");
        passwordList.put("starbucks", "3");;
        passwordList.put("mcdonalds", "4");
        return passwordList;
    }

    public static RestaurantManager readResAndFood() throws  IOException{
        RestaurantManager restaurantManager = new RestaurantManager();
        //declare scanner
        Scanner scanner = new Scanner(System.in);
        //declare reader
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        //input for restaurants
        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String [] array = line.split(",", -1);
            if(array.length == 8){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5], array[6], array[7]);
                restaurantManager.addRestaurant(r);
            }else if(array.length == 7){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5], array[6]);
                restaurantManager.addRestaurant(r);
            }else if(array.length == 6){
                Restaurant r = new Restaurant(Integer.parseInt(array[0]), array[1], Double.parseDouble(array[2]), array[3], array[4], array[5]);
                restaurantManager.addRestaurant(r);
            }
        }
        br.close();

        //input for foods
        br = new BufferedReader(new FileReader(INPUT_FILE_NAME2));

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            String[] array = line.split(",", -1);

            int restaurantId = Integer.parseInt(array[0]);
            String category = array[1];
            String foodName = array[2];


            if(array.length > 4){
                for(int i = 3; i < array.length - 1; i++){
                    foodName = foodName.concat("," + array[i]);
                }
            }
            double price = Double.parseDouble(array[array.length - 1]);
            Food f = new Food(restaurantId, category, foodName, price);

            for(int i = 0 ; i<restaurantManager.getRestaurants().size(); i++){
                if(restaurantManager.getRestaurants().get(i).getId() == Integer.parseInt(array[0])){
                    restaurantManager.getRestaurants().get(i).addMenuItems(f);
                }

            }

        }

        br.close();
        return restaurantManager;
    }

}