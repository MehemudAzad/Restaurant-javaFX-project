package com.example.main_project_final.controllers;

import com.example.main_project_final.CustomerMain;
import com.example.main_project_final.backend.Food;
import com.example.main_project_final.backend.Restaurant;
import com.example.main_project_final.utils.DatabaseDTO;
import com.example.main_project_final.utils.NetworkUtil;
import com.example.main_project_final.utils.OrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerRestaurantController implements Initializable {
    private CustomerMain main;

    @FXML
    private Label nameLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ListView<String> orderListView;
    @FXML
    private TextField filteredField;
    @FXML
    private TextField categoryFilterField;
    @FXML
    private TextField lowerLimitFilter;
    @FXML
    private TextField upperLimitFilter;
    @FXML
    private TableView<Food> foodOrderTable;
    @FXML
    private TableColumn<Food, String> category;

    @FXML
    private TableColumn<Food, String> name;

    @FXML
    private TableColumn<Food, Double> price;

    @FXML
    private TableColumn actionCol;
    @FXML
    private Label stateLabel;


    @FXML
    void placeOrder(ActionEvent event) {
        try{
            main.getNetworkUtil().write(main.orderDTO);
        }catch (Exception e){
            System.out.println("Exception found in placing order");
        }
        main.orderDTO = new OrderDTO(main.restaurantManager.searchRestaurantByName(main.orderDTO.getRestaurant().getName()), main.customerUserName);
        orderListView.getItems().clear();
    }

    @FXML
    void addOrder(MouseEvent event) throws Exception {


    }




    private final ObservableList<Food> dataList = FXCollections.observableArrayList();

    @FXML
    void goBackHome(ActionEvent event) {
        try{
            main.showHomePage(main.customerUserName ,main.restaurantManager);
        }catch (Exception e){
            System.out.println("Exception in goBack ");
        }
    }
    public void init(String restName){
            dataList.addAll(main.restaurantManager.searchRestaurantByName(restName).getMenuItems());
    }

    public void setMain(CustomerMain main) {
        this.main = main;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));


//        TableColumn<Food, Void> actionColumn = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<Food, Void>() {
            private final Button editButton = new Button("ADD TO CART");

            {
                editButton.setOnAction(event -> {
                    Food item = getTableView().getItems().get(getIndex());
                    // Handle the button click event (e.g., open a dialog for editing)
                    System.out.println("Edit button clicked for item: " + item.getName());
                    orderListView.getItems().add(item.getName());

                    if(main.orderDTO.getOrderList().get(item) == null){
                        item.incrementOrderCnt();
                        main.orderDTO.addFoodToOrder(item, 1);
                    }else{
                        item.incrementOrderCnt();
                        main.orderDTO.addFoodToOrder(item, main.orderDTO.getOrderList().get(item) + 1);
                    }

                    main.orderDTO.print();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

//        foodOrderTable.getColumns().add(actionCol);



        //dynamically display on the right side
        foodOrderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Item: " + newValue.getName());
                nameLabel.setText(newValue.getName());

                categoryLabel.setText(newValue.getCategory());

                double price = newValue.getPrice();
                String priceString = String.valueOf(price);
                priceLabel.setText(priceString);

                //set Image if time left

            }
        });

        FilteredList<Food> filteredData = new FilteredList<>(dataList, b-> true);
            filteredField.textProperty().addListener((observable, oldValue, newValue) ->{
                filteredData.setPredicate(food -> {
                    if(newValue == null || newValue.isEmpty() ){
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if(food.getName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                        return true;
                    }else{
                        return false;
                    }
                });

            });
        categoryFilterField.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(food -> {
                if(newValue == null || newValue.isEmpty() ){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(food.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                    return false;
                }
            });

        });

        lowerLimitFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(food -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                double lowerLimit = Integer.parseInt(newValue);
                double upperLimit = Integer.MAX_VALUE; // Set a high upper limit by default

                // Check if the upperLimitFilter has a value and parse it
                String upperLimitText = upperLimitFilter.getText();
                if (!upperLimitText.isEmpty()) {
                    upperLimit = Integer.parseInt(upperLimitText);
                }

                double foodPrice = food.getPrice();

                return foodPrice >= lowerLimit && foodPrice <= upperLimit;
            });
        });

// Add a listener to the upperLimitFilter textProperty
        upperLimitFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(food -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                double upperLimit = Integer.parseInt(newValue);
                double lowerLimit = Integer.MIN_VALUE; // Set a low lower limit by default

                // Check if the lowerLimitFilter has a value and parse it
                String lowerLimitText = lowerLimitFilter.getText();
                if (!lowerLimitText.isEmpty()) {
                    lowerLimit = Integer.parseInt(lowerLimitText);
                }

                double foodPrice = food.getPrice();

                return foodPrice >= lowerLimit && foodPrice <= upperLimit;
            });
        });


        SortedList<Food> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(foodOrderTable.comparatorProperty());

        foodOrderTable.setItems(sortedData);
    }


    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showCustomerLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




