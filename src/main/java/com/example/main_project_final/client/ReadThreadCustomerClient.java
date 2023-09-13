package com.example.main_project_final.client;

import com.example.main_project_final.CustomerMain;
import com.example.main_project_final.backend.RestaurantManager;
import com.example.main_project_final.utils.DatabaseDTO;
import com.example.main_project_final.utils.LoginDTO;
import com.example.main_project_final.utils.customerLoginDTO;
import javafx.application.Platform;
import java.io.IOException;

public class ReadThreadCustomerClient implements Runnable {
    private final Thread thr;
    private final CustomerMain main;

    public ReadThreadCustomerClient(CustomerMain main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof customerLoginDTO) {
                        customerLoginDTO loginDTO = (customerLoginDTO) o;
                        System.out.println(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());
                        if (loginDTO.isStatus()) {
                            Object myobj;
                            try {
                                myobj = main.getNetworkUtil().read();
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            //read restaurant manager from the server
                            if (myobj instanceof RestaurantManager) {
                                RestaurantManager restaurantManager = (RestaurantManager) myobj;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            main.showHomePage(loginDTO.getUserName(),restaurantManager);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                main.showAlert();
                            }
                        }
                        //read restaurant manager from the server
//                        if (o instanceof RestaurantManager) {
//                            RestaurantManager restaurantManager = (RestaurantManager) o;
//                            Platform.runLater(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (true) {
//                                        try {
//                                            main.showHomePage(restaurantManager);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    } else {
//                                        main.showAlert();
//                                    }
//
//                                }
//                            });
//                        }
                    }
                }
            }
        }catch (Exception e) {
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



