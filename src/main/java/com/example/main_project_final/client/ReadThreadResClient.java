package com.example.main_project_final.client;

import com.example.main_project_final.RestMain;
import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.backend.RestaurantManager;
import com.example.main_project_final.utils.LoginDTO;
import com.example.main_project_final.utils.OrderDTO;
import com.example.main_project_final.utils.customerLoginDTO;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadThreadResClient implements Runnable {
    private final Thread thr;
    private final RestMain main;

    public ReadThreadResClient(RestMain main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        System.out.println("hello");
        try {
            while (true) {
                Object o = null;
                try {
                    o = main.getNetworkUtil().read();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
//                        System.out.println(loginDTO.getUserName());
//                        System.out.println(loginDTO.isStatus());
                        if (loginDTO.isStatus()) {
                            Object myObj;
                            try {
                                myObj = main.getNetworkUtil().read();
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            //read restaurant manager from the server
                            if (myObj instanceof Restaurant) {
                                System.out.println("Restaurant Received by restaurant read server");
                                var restaurant = (Restaurant) myObj;

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            main.showHomePage(restaurant.getName(),restaurant);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                main.showAlert();
                            }
                        }

                    }

            }

                    if(o instanceof OrderDTO){
                        System.out.println("Order received by restaurant");
                        //extract information from the orderDTO
                        var orderDTO = (OrderDTO) o;
                        String restName = orderDTO.getRestaurant().getName();
                        String customerName = orderDTO.getCustomerName();
                        Restaurant restaurant = orderDTO.getRestaurant();
                        //update the orderCNT
                        var menuList = main.restaurant.getMenuItems();
                        var foodOrderMap = orderDTO.getOrderList();

                        if (!main.foodOrderedMap.containsKey(restName)) {
                            // If not, create a new list and put it in the map
                            main.foodOrderedMap.put(restName, new ArrayList<>());
                        }
                        if (!main.customerOrderedMap.containsKey(restName)) {
                            // If not, create a new list and put it in the map
                            main.customerOrderedMap.put(restName, new ArrayList<>());
                        }

                        // Get the list associated with the key
                        List<String> foodList = main.foodOrderedMap.get(restName);

                        // Get the list associated with the key
                        List<String> customerList = main.customerOrderedMap.get(restName);
                        customerList.add(customerName);
                        main.customerOrderedMap.put(restName, customerList);


                        foodOrderMap.forEach((food, orderCount) -> {
                            /*
                            main.orderedFoodList.add(food.getName());
                            */

                            // Add the value to the list
                            foodList.add(food.getName());
                            // Increment the order count for each Food object
                            food.increaseOrderCnt(orderCount);
                            for(Food f: menuList){
                                if(f.getName().equals(food.getName())){
                                    System.out.println("hello 111");
                                    f.increaseOrderCnt(orderCount);
                                }
                            }
//                            System.out.println("previous orderCnt: " + food.getOrderCnt());
//                            System.out.println(food.getName() + "current order: " + orderCount);
//                            System.out.println();
                        });

                        // Put the modified list back into the map
                        main.foodOrderedMap.put(restName, foodList);
//                        System.out.println("");

                        //add ordered foods to orderedFoodItem list


                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    main.showHomePage(restName,main.restaurant);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



