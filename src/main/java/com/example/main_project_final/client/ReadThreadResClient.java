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
                        Restaurant restaurant = orderDTO.getRestaurant();
                        //update the orderCNT
                        var menuList = main.restaurant.getMenuItems();
                        var foodOrderMap = orderDTO.getOrderList();


                        foodOrderMap.forEach((food, orderCount) -> {
                            main.orderedFoodList.add(food.getName());
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



