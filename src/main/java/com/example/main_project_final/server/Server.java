package com.example.main_project_final.server;

import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.backend.RestaurantManager;
import com.example.main_project_final.utils.FileOperations;
import com.example.main_project_final.utils.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    /*********************
      setters and getters
     *********************/
    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public ConcurrentHashMap<String, NetworkUtil> getRestaurantNetworkMap(){
        return restaurantNetworkMap;
    }
    public ConcurrentHashMap<String, String> getPasswordList() {
        return passwordList;
    }

    private volatile int customerClientCount;
    private volatile int restaurantClientCount;
    private RestaurantManager restaurantManager;
    private ConcurrentHashMap passwordList;
    private List<String> restaurantNamesList;
    //we keep a networkUtil and name hashMap inorder to keep track of the corresponding networkUtils
    private ConcurrentHashMap<String,NetworkUtil> restaurantNetworkMap;
    private ConcurrentHashMap<String,NetworkUtil> customerNetworkMap;

    private ServerSocket serverSocket;


    //constructor
    Server() throws IOException {
        customerClientCount = 0;
        restaurantClientCount = 0;
        restaurantNetworkMap = new ConcurrentHashMap<>();
        passwordList = new ConcurrentHashMap<>();

        /*-=-=-=-=-=-=-=-=-=-=-=-=-=
            read from file
        -=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
        restaurantManager = FileOperations.readResAndFood();
        passwordList = FileOperations.setPasswords();

//        1,KFC,4.3,$$$,98531,Chicken,Fast Food,Family Meals
//        2,IHOP,4.3,$$,77494,Breakfast and Brunch,Family Meals,Burgers
//        3,Starbucks,4.9,$,99218,Coffee and Tea,Breakfast and Brunch,Bakery
//        4,McDonalds,4.7,$,98346,Burgers,Fast Food,
        System.out.println("Foods in the server: ");
        restaurantManager.displayTotalNumberOfFoodItems();
        try {
            serverSocket = new ServerSocket(33333);//open the server socket
            System.out.println("Server is waiting...");
            while (true) {
                Socket clientSocket = serverSocket.accept();//we are accepting the client now
                System.out.println("Received a client");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }
    //then we have to serve it as well here's how we do it we create a separate
    // read thread for each server
    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(clientSocket, this, networkUtil);
    }


    public static void main(String[] args) throws IOException {
        new Server();
    }
}
