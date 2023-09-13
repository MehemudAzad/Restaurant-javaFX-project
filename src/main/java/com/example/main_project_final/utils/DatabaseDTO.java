package com.example.main_project_final.utils;

import com.example.main_project_final.backend.RestaurantManager;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseDTO  implements Serializable {
    RestaurantManager restaurantManager;
    ConcurrentHashMap<String, NetworkUtil> restaurantNetworkMap;

    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public void setRestaurantManager(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    public ConcurrentHashMap<String, NetworkUtil> getRestaurantNetworkMap() {
        return restaurantNetworkMap;
    }

    public void setRestaurantNetworkMap(ConcurrentHashMap<String, NetworkUtil> restaurantNetworkMap) {
        this.restaurantNetworkMap = restaurantNetworkMap;
    }

    public DatabaseDTO(RestaurantManager restaurantManager, ConcurrentHashMap<String, NetworkUtil> restaurantNetworkMap){
        this.restaurantManager = restaurantManager;
        this.restaurantNetworkMap = restaurantNetworkMap;
    }

    public DatabaseDTO(){
        this.restaurantManager = null;
        this.restaurantNetworkMap = new ConcurrentHashMap<>();
    }

}
