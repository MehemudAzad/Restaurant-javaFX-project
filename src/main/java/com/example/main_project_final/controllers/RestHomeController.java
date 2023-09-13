package com.example.main_project_final.controllers;

import com.example.main_project_final.RestMain;
import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.utils.LogoutDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RestHomeController {

    private RestMain main;

    @FXML
    private Label categoryText;
    @FXML
    private Label nameText;
    @FXML
    private Label priceText;
    @FXML
    private Label orderText;
    @FXML
    private ListView<String> topCustomersList;
    @FXML
    private ListView<String> orderList;
    @FXML
    private ImageView imageViewHome;
    @FXML
    private TableView<Food> restaurantTable;

    @FXML
    private TableColumn<Food, String> tableCategory = new TableColumn<>("Category");

    @FXML
    private TableColumn<Food, String> tableFood = new TableColumn<>("Name");

    @FXML
    private TableColumn<Food, Integer> tableOrders = new TableColumn<>("Orders");

    @FXML
    private TableColumn<Food, Double> tablePrice = new TableColumn<>("Price");
    @FXML
    private Label restName;

    private Image image;

    @FXML
    private Button button;

    private boolean init = true;

    @FXML
    void displayDetails(MouseEvent event) {
        restaurantTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Item: " + newValue.getName());
                nameText.setText(newValue.getName());
                categoryText.setText(newValue.getCategory());

                double price = newValue.getPrice();
                String priceString = String.valueOf(price);
                priceText.setText(priceString);

                int orders = newValue.getOrderCnt();
                String orderString = String.valueOf(orders);
                orderText.setText(orderString);
                //image here
            }
        });
    }


    public void init(String msg) {
        restName.setText(msg);
//        if(main.restaurant.getName().equalsIgnoreCase("KFC")){
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/resources/com/example/main_project_final/kfc.png")));
//            imageViewHome.setImage(image);
//        }else if(main.restaurant.getName().equalsIgnoreCase("IHOP")){
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/resources/com/example/main_project_final/ihop.png")));
//            imageViewHome.setImage(image);
//        }else if(main.restaurant.getName().equalsIgnoreCase("starbucks")){
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/resources/com/example/main_project_final/java-logo-vector.png")));
//            imageViewHome.setImage(image);
//        }else if(main.restaurant.getName().equalsIgnoreCase("mcdonalds")){
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/resources/com/example/main_project_final/java-logo-vector.png")));
//            imageViewHome.setImage(image);
//        }
        //I need to change these
//        Image img = new Image(RestMain.class.getResourceAsStream("1.png"));
//        image.setImage(img);
        if (!main.foodOrderedMap.containsKey(msg)) {
            // If not, create a new list and put it in the map
            main.foodOrderedMap.put(msg, new ArrayList<>());
        }
        List<String> list = main.foodOrderedMap.get(msg);
        for(var s: list){
            orderList.getItems().add(s);
        }


        if (!main.customerOrderedMap.containsKey(msg)) {
            // If not, create a new list and put it in the map
            main.customerOrderedMap.put(msg, new ArrayList<>());
        }
        List<String> customerList = main.customerOrderedMap.get(msg);
        for(var s: customerList){
            topCustomersList.getItems().add(s);
        }
    }
    public void initializeColumns() {
        tableFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableOrders.setCellValueFactory(new PropertyValueFactory<>("orderCnt"));

//        restaurantTable.getColumns().addAll(tableFood, tableCategory, tablePrice, tableOrders);
    }

    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }
        restaurantTable.setEditable(true);
        restaurantTable.setItems(FXCollections.observableArrayList(main.restaurant.getMenuItems()));
    }

//    @FXML
//    void logoutAction(ActionEvent event) {
//        try {
//            main.showLoginPage();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    public void logoutAction(ActionEvent event) {
        var lDTO = new LogoutDTO(main.restaurant.getName());
        lDTO.setStatus(true);

        try {
            main.getNetworkUtil().write(lDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setMain(RestMain main) {
        this.main = main;
    }

}
