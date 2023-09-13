package com.example.main_project_final.controllers;

import com.example.main_project_final.RestMain;
import com.example.main_project_final.utils.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

//we write a init function for all controllers that will initialize it
//we will call the main function and use the functions written there to out need
//public class RestLoginController {
//    private RestMain main; //we need to do this for all the controller
//
//}

public class RestLoginController {
    //the main function
    private RestMain main;

    @FXML
    private ImageView loginImage;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

@FXML
void loginAction(ActionEvent event) {
        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void init(){
//    Image image = new Image(getClass().getResourceAsStream("off.png"));
//    loginImage.setImage(image);
}

@FXML
void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    public void setMain(RestMain main) {
        this.main = main;
    }

}
