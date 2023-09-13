package com.example.main_project_final;

import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.backend.RestaurantManager;
import com.example.main_project_final.client.ReadThreadCustomerClient;
import com.example.main_project_final.controllers.CustomerRestaurantController;
import com.example.main_project_final.controllers.HomeController;
import com.example.main_project_final.controllers.LoginController;
import com.example.main_project_final.utils.DatabaseDTO;
import com.example.main_project_final.utils.NetworkUtil;
import com.example.main_project_final.utils.OrderDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerMain extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;

    public String customerUserName;
    public RestaurantManager restaurantManager;
    public ConcurrentHashMap<String, NetworkUtil> restaurantNetworkMap;
    public List<Food> menuList;
    public OrderDTO orderDTO;

    public Stage getStage() {
        return stage;
    }
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showCustomerLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadCustomerClient(this);
    }

    public void showCustomerLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login-page.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("LoginCustomer");
        stage.setScene(new Scene(root, 700, 500));
        stage.show();
    }

    public void showHomePage(String userName, RestaurantManager restaurantManager) throws Exception {
        this.customerUserName = userName;//store the name
        this.restaurantManager = restaurantManager;
        for(int i =0 ; i<restaurantManager.getRestaurants().size(); i++){
            Restaurant restaurant = restaurantManager.getRestaurants().get(i);
            System.out.println(restaurant.getName());
            for(int j =0 ; j<restaurant.getMenuItems().size(); j++){
                Food f = restaurant.getMenuItems().get(j);
                System.out.println(f.getName() + " orders : "+ f.getOrderCnt());
            }
        }
        restaurantManager.displayTotalNumberOfFoodItems();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("customer-home.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HomeController controller = loader.getController();
        controller.setMain(this);
        controller.load();
        controller.init(userName);


        // Set the primary stage
        stage.setTitle("Customer Home");
        stage.setScene(new Scene(root, 1100, 850));
        stage.show();
    }
//
//    public void showHomePage(String userName, DatabaseDTO databaseDTO) throws Exception {
//        this.customerUserName = userName;//store the name
//        this.restaurantManager = databaseDTO.getRestaurantManager();
//        this.restaurantNetworkMap = databaseDTO.getRestaurantNetworkMap();
//
//
//        for(int i =0 ; i<restaurantManager.getRestaurants().size(); i++){
//            Restaurant restaurant = restaurantManager.getRestaurants().get(i);
//            System.out.println(restaurant.getName());
//            for(int j =0 ; j<restaurant.getMenuItems().size(); j++){
//                Food f = restaurant.getMenuItems().get(j);
//                System.out.println(f.getName() + " orders : "+ f.getOrderCnt());
//            }
//        }
//        restaurantManager.displayTotalNumberOfFoodItems();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("customer-home.fxml"));
//        Parent root = loader.load();
//
//        // Loading the controller
//        HomeController controller = loader.getController();
//        controller.setMain(this);
//        controller.load();
//        controller.init(userName);
//
//
//        // Set the primary stage
//        stage.setTitle("Customer Home");
//        stage.setScene(new Scene(root, 1100, 850));
//        stage.show();
//    }

    public void showRestaurantPage(String restName) throws Exception {
        //initialize the orderDTO again
        orderDTO = new OrderDTO(restaurantManager.searchRestaurantByName(restName), customerUserName);
//        menuList = restaurantManager.searchRestaurantByName(restName).getMenuItems();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("test.fxml"));
        Parent root = loader.load();
        System.out.println(restName);

        // Loading the controller
        CustomerRestaurantController controller = loader.getController();
        controller.setMain(this);
        controller.init(restName);

//        controller.load(restName);

        // Set the primary stage
        stage.setTitle("Restaurant Details Page");
        stage.setScene(new Scene(root, 1100, 800));
        stage.show();
    }
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }
}
