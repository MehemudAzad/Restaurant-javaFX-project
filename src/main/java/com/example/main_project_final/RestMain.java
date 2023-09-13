package com.example.main_project_final;

import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.client.ReadThreadResClient;
import com.example.main_project_final.controllers.RestHomeController;
import com.example.main_project_final.controllers.RestLoginController;
import com.example.main_project_final.utils.NetworkUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RestMain extends Application {

    //basic necessities of a main function --> we need a stage a restaurantName and a networkUtil
    public Restaurant restaurant;
    private Stage stage;
    private NetworkUtil networkUtil;
    public List<String> orderedFoodList = new ArrayList<>();
    public List<String> customerList = new ArrayList<>();
    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        System.out.println("Here In the restaurant");
        connectToServer();//connecting to server
        System.out.println("connected to server");
        showLoginPage();//showing the login page at the start
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadResClient(this);//in this thread we will listen to all the data from the server
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("res-login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        RestLoginController controller = loader.getController();
        controller.setMain(this);
        controller.init();

        // Set the primary stage
        stage.setTitle("Restaurant Login");
        stage.setScene(new Scene(root, 800, 595));
        stage.show();
    }

    public void showHomePage(String userName, Restaurant restaurant) throws Exception {
        System.out.println(userName);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("res-home.fxml"));
        Parent root = loader.load();
        this.restaurant = restaurant;

        RestHomeController controller = loader.getController();
        controller.setMain(this);
        controller.load();
        controller.init(userName);
        // Set the primary stage
        stage.setTitle("Restaurant Home");
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
