package com.example.main_project_final.controllers;//package com.example.demo1;

import com.example.main_project_final.CustomerMain;
import com.example.main_project_final.RestMain;
import com.example.main_project_final.utils.customerLoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    private CustomerMain main;

    @FXML
    private TextField customerUserName;

    @FXML
    private Button loginButton;

    public void setMain(CustomerMain main) {
        this.main = main;
    }

    @FXML
    void loginAction(ActionEvent event) {
        System.out.println("login customer action button");
        String userName = customerUserName.getText();
        customerLoginDTO cusLoginDTO = new customerLoginDTO();
        cusLoginDTO.setUserName(userName);
        try {
            main.getNetworkUtil().write(cusLoginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}