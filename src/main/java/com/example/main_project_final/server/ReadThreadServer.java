package com.example.main_project_final.server;

import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.RestaurantManager;
import com.example.main_project_final.utils.*;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public ConcurrentHashMap<String, String> userMapPassword;
    Server server;
    Socket clientSocket;
    RestaurantManager restaurantManager;
//    List<String> restaurantNamesList;
    ConcurrentHashMap<String,NetworkUtil> restaurantNetworkMap;

    ReadThreadServer(Socket clientSocket, Server server, NetworkUtil networkUtil){
        this.clientSocket = clientSocket;
        this.server = server;
        this.networkUtil = networkUtil;
        this.userMapPassword = server.getPasswordList();
        this.restaurantNetworkMap = server.getRestaurantNetworkMap();
        this.restaurantManager = server.getRestaurantManager();
        thr = new Thread(this);
        thr.start();
    }


    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    //this is sus code
                    if (o instanceof LoginDTO) {
                        var loginDTO = (LoginDTO) o;//we typecast it to be sure since multiple instances might be present aswell
                        String password = userMapPassword.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        System.out.println(loginDTO.isStatus());
                        System.out.println("login status set");


                        if (loginDTO.isStatus()) {
                            restaurantNetworkMap.put(loginDTO.getUserName().toLowerCase(), networkUtil);
//                            networkUtil.write(productionCompanyMap.get(loginDTO.getUserName()));
                            System.out.println(loginDTO.getUserName());
                            try{
                                networkUtil.write(restaurantManager.searchRestaurantByName(loginDTO.getUserName()));
                            }catch (Exception e){
                                System.out.println("Exception in loginDTO server read thread");
                            }
                           //writing that particular restaurant to the restaurant client
                        }else{
                            networkUtil.write(loginDTO);
                        }
                    }
                    if (o instanceof LoginDTO) {
                        var loginDTO = (LoginDTO) o;//we typecast it to be sure since multiple instances might be present aswell
                        String password = userMapPassword.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
//                        System.out.println("login status set");
                        networkUtil.write(loginDTO);

                        if (loginDTO.isStatus()) {
                            try{
                                restaurantNetworkMap.put(loginDTO.getUserName().toLowerCase(), networkUtil);
                                networkUtil.write(restaurantManager.searchRestaurantByName(loginDTO.getUserName()));
                                System.out.println("Restaurant "+ restaurantManager.searchRestaurantByName(loginDTO.getUserName()).getName() + "logged in");
                            }catch (Exception e){
                                System.out.println("Exception in loginDTO server read thread");
                            }
                            //writing that particular restaurant to the restaurant client
                        }else{
                            networkUtil.write(loginDTO);
                        }
                    }
//
                    if (o instanceof customerLoginDTO) {
                        customerLoginDTO loginDTO = (customerLoginDTO) o;
                        loginDTO.setStatus(true);
                        try{
                            networkUtil.write(loginDTO);
                        }catch (Exception e){
                            System.out.println("Exception in customer login");
                        }
//
                        if (loginDTO.isStatus()) {
                            DatabaseDTO databaseDTO = new DatabaseDTO(this.restaurantManager, this.restaurantNetworkMap);
                            try{

                                networkUtil.write(restaurantManager);
                            }catch (Exception e){
                                System.out.println("Exception in customer login sending restaurant manager");
                            }
                        }
//                        if (loginDTO.isStatus()) {
//                            DatabaseDTO databaseDTO = new DatabaseDTO(this.restaurantManager, this.restaurantNetworkMap);
//                            try{
//                                networkUtil.write(databaseDTO);
//                            }catch (Exception e){
//                                System.out.println("Exception in customer login sending restaurant manager");
//                            }
//                        }

                    }

                    if(o instanceof OrderDTO){
                        System.out.println("order received from customer");
                        var orderDTO = (OrderDTO) o;
                        //get the restaurant socket and write there
                        NetworkUtil networkUtil = restaurantNetworkMap.get(orderDTO.getRestaurant().getName().toLowerCase());
                        String restName = orderDTO.getRestaurant().getName();
                        var menuList = restaurantManager.searchRestaurantByName(restName).getMenuItems();
                        var foodOrderMap = orderDTO.getOrderList();
                        foodOrderMap.forEach((food, orderCount) -> {
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
                        //write to the restaurant client
                        if(networkUtil == null)
                        {
                            System.out.println("restaurant not found");
                        }else{
                            System.out.println("order placed successfully to " + orderDTO.getRestaurant().getName());
                            try{
                                networkUtil.write(orderDTO);
                            }catch (Exception e){
                                System.out.println("Exception in orderDTO");
                            }
                        }

                    }
                    if(o instanceof LogoutDTO){
                        var logoutDTO = (LogoutDTO) o;
                        System.out.println("restaurant logout " + logoutDTO.getRestaurantName());
                        restaurantNetworkMap.remove(logoutDTO.getRestaurantName().toLowerCase());
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
