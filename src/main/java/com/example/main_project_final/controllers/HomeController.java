package com.example.main_project_final.controllers;

import com.example.main_project_final.CustomerMain;
import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.utils.LogoutDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    private CustomerMain main;
    @FXML
    private Label userText;
    @FXML
    private TableView<Restaurant> restaurantTableView;
    @FXML
    private TableView<Food> foodTableView;
    //food column
    @FXML
    private TextField foodNameFilterField;
    @FXML
    private TableColumn<Food, String> foodNameCol;
    @FXML
    private TableColumn<Food, String> foodCategoryCol;
    @FXML
    private TableColumn<Food, Double> foodPriceCol;
    @FXML
    private TableColumn<Food, Integer> foodRestaurantNameCol;

    //restaurant column
    @FXML
    private TableColumn<Restaurant, String> category1;

    @FXML
    private TableColumn<Restaurant, String> category2;

    @FXML
    private TableColumn<Restaurant, String> category3;

    @FXML
    private TableColumn<Restaurant, String> name;

    @FXML
    private TableColumn<Restaurant, String> price;

    @FXML
    private TableColumn<Restaurant, Double> score;

    @FXML
    private TextField scoreFilterField;
    @FXML
    private TextField nameFilterField;
    @FXML
    private TextField categoryFilterField;
    @FXML
    private TextField priceFilterField;

    private final ObservableList<Restaurant> dataList = FXCollections.observableArrayList();
    private final ObservableList<Food> foodList = FXCollections.observableArrayList();

    public void init(String msg) {
        userText.setText(msg);
        dataList.addAll(main.restaurantManager.getRestaurants());
        for(Restaurant r: main.restaurantManager.getRestaurants()){
            foodList.addAll(r.getMenuItems());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("price"));
        category1.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("category1"));
        category2.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("category2"));
        category3.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("category3"));
        score.setCellValueFactory(new PropertyValueFactory<Restaurant, Double>("score"));



//       /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//            Show restaurant Details
//        -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
        foodNameCol.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
        foodCategoryCol.setCellValueFactory(new PropertyValueFactory<Food, String>("category"));
        foodPriceCol.setCellValueFactory(new PropertyValueFactory<Food, Double>("price"));
        foodRestaurantNameCol.setCellValueFactory(new PropertyValueFactory<Food, Integer>("restaurantId"));
        /*-=-=-=-=-=-=-=-=-=-=-=-=
            search foods by name
        -=-=-=-=-=-=-=-=-=-=-=-=-=*/

        {
            FilteredList<Food> filteredDataFood = new FilteredList<>(foodList, b -> true);
            foodNameFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredDataFood.setPredicate(food -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return food.getName().toLowerCase().indexOf(lowerCaseFilter) != -1;
                });

            });
            SortedList<Food> sortedDataFood = new SortedList<>(filteredDataFood);

            sortedDataFood.comparatorProperty().bind(foodTableView.comparatorProperty());

            foodTableView.setItems(sortedDataFood);

        }


        /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            search restaurant options
        -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
        //search by name
        {
            FilteredList<Restaurant> filteredData = new FilteredList<>(dataList, b -> true);
            nameFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(restaurant -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    return restaurant.getName().toLowerCase().indexOf(lowerCaseFilter) != -1;
                });

            });

            //search by category
            categoryFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(restaurant -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (restaurant.getCategory1().toLowerCase().indexOf(lowerCaseFilter) != -1 || restaurant.getCategory2().toLowerCase().indexOf(lowerCaseFilter) != -1 || restaurant.getCategory3().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });

            });

            //search by price
            priceFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(restaurant -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    return restaurant.getPrice().equalsIgnoreCase(newValue);
                });
            });

            //search by score
            scoreFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(restaurant -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    double foodScore = restaurant.getScore();
                    double score = Double.parseDouble(newValue);

                    return foodScore >= score;
                });
            });

            SortedList<Restaurant> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(restaurantTableView.comparatorProperty());

            restaurantTableView.setItems(sortedData);
        }
    }
    private boolean init = true;


//    public void initializeColumns() {
//
//
//
//    }

    public void load() {
        if (init) {
            init = false;
        }
        restaurantTableView.setEditable(true);
//        restaurantTableView.setItems(FXCollections.observableArrayList(main.restaurantManager.getRestaurants()));
    }

    Integer index;
    @FXML
    void getItem(MouseEvent event) throws Exception {
        System.out.println("hello in mouse event");
        index = restaurantTableView.getSelectionModel().getSelectedIndex();
        if(index<= -1) return;

        String restName = name.getCellData(index).toString();
        System.out.println(restName);
        try{

        }catch (Exception e){
            System.out.println("Exception in mouse event call");
        }
        main.showRestaurantPage(restName);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showCustomerLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(CustomerMain main) {
        this.main = main;
    }
}
